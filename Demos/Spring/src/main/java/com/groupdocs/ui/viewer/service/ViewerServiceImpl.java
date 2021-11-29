package com.groupdocs.ui.viewer.service;

import com.groupdocs.ui.config.GlobalConfiguration;
import com.groupdocs.ui.exception.TotalGroupDocsException;
import com.groupdocs.ui.model.response.PageDescriptionEntity;
import com.groupdocs.ui.util.Utils;
import com.groupdocs.ui.viewer.cache.FileViewerCache;
import com.groupdocs.ui.viewer.cache.ViewerCache;
import com.groupdocs.ui.viewer.config.ViewerConfiguration;
import com.groupdocs.ui.viewer.exception.ReadWriteException;
import com.groupdocs.ui.viewer.model.request.LoadDocumentPageRequest;
import com.groupdocs.ui.viewer.model.request.LoadDocumentRequest;
import com.groupdocs.ui.viewer.model.request.RotateDocumentPagesRequest;
import com.groupdocs.ui.viewer.model.response.FileDescriptionEntity;
import com.groupdocs.ui.viewer.model.response.LoadDocumentEntity;
import com.groupdocs.ui.viewer.util.PagesInfoStorage;
import com.groupdocs.ui.viewer.util.ViewerUtils;
import com.groupdocs.ui.viewer.viewer.CustomViewer;
import com.groupdocs.ui.viewer.viewer.HtmlViewer;
import com.groupdocs.ui.viewer.viewer.PngViewer;
import com.groupdocs.viewer.License;
import com.groupdocs.viewer.exception.IncorrectPasswordException;
import com.groupdocs.viewer.exception.PasswordRequiredException;
import com.groupdocs.viewer.fonts.FolderFontSource;
import com.groupdocs.viewer.fonts.FontSettings;
import com.groupdocs.viewer.fonts.FontSource;
import com.groupdocs.viewer.fonts.SearchOption;
import com.groupdocs.viewer.results.Page;
import com.groupdocs.viewer.results.ViewInfo;
import com.groupdocs.viewer.utils.PathUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ViewerServiceImpl implements ViewerService {
    private static final Logger logger = LoggerFactory.getLogger(ViewerServiceImpl.class);

    @Autowired
    private GlobalConfiguration globalConfiguration;
    @Autowired
    private ViewerConfiguration viewerConfiguration;

    private Path mCachePath;
    private boolean isViewerLicenseSet = false;

    private static int mergeAngles(int currentAngle, int angle) {
        switch (currentAngle) {
            case 0:
                return (angle == 90 ? 90 : 270);
            case 90:
                return (angle == 90 ? 180 : 0);
            case 180:
                return (angle == 90 ? 270 : 90);
            case 270:
                return (angle == 90 ? 0 : 180);
        }
        return 0;
    }

    private static PageDescriptionEntity getPageInfo(Page page, String pagesInfoPath) {

        int currentAngle = PagesInfoStorage.loadPageAngle(pagesInfoPath, page.getNumber());

        PageDescriptionEntity pageDescriptionEntity = new PageDescriptionEntity();

        pageDescriptionEntity.setNumber(page.getNumber());

        // we intentionally use the 0 here because we plan to rotate only the page background using height/width
        pageDescriptionEntity.setAngle(0);
        pageDescriptionEntity.setHeight(currentAngle == 0 || currentAngle == 180 ? page.getHeight() : page.getWidth());
        pageDescriptionEntity.setWidth(currentAngle == 0 || currentAngle == 180 ? page.getWidth() : page.getHeight());

        return pageDescriptionEntity;
    }

    /**
     * Initializing fields after creating configuration objects
     */
    @PostConstruct
    public void init() {
        setLicense();
        // Register custom fonts
        if (!StringUtils.isEmpty(viewerConfiguration.getFontsDirectory())) {
            FontSource fontSource = new FolderFontSource(viewerConfiguration.getFontsDirectory(), SearchOption.TOP_FOLDER_ONLY);
            FontSettings.setFontSources(fontSource);
        }
        //
        mCachePath = Paths.get(viewerConfiguration.getFilesDirectory(), viewerConfiguration.getCacheFolderName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FileDescriptionEntity> getFileList(String path) {
        final File filesDirectory = new File(PathUtils.combine(viewerConfiguration.getFilesDirectory(), path));

        try {
            final File[] files = filesDirectory.listFiles();
            if (files == null) {
                throw new TotalGroupDocsException("Can't list files");
            }

            List<FileDescriptionEntity> filesList = createListOfFileDescriptionEntities(files);

            // Sort by name and to make directories to be before files
            Collections.sort(filesList, new Comparator<FileDescriptionEntity>() {
                @Override
                public int compare(FileDescriptionEntity o1, FileDescriptionEntity o2) {
                    if (o1.isIsDirectory() && !o2.isIsDirectory()) {
                        return -1;
                    }
                    if (!o1.isIsDirectory() && o2.isIsDirectory()) {
                        return 1;
                    }
                    return o1.getName().compareTo(o2.getName());
                }
            });
            return filesList;
        } catch (IOException e) {
            logger.error("Exception in getting file list", e);
            throw new TotalGroupDocsException(e.getMessage(), e);
        }
    }

    private List<FileDescriptionEntity> createListOfFileDescriptionEntities(File[] files) throws IOException {
        List<FileDescriptionEntity> filesList = new ArrayList<>();
        for (File file : files) {
            // check if current file/folder is not hidden
            if (
                    !(file.getName().equals(viewerConfiguration.getCacheFolderName()))
                            && !(file.getName().equals(viewerConfiguration.getResourcesFolderName()))
                            && !file.getName().startsWith(".")
                            && !file.isHidden()) {
                FileDescriptionEntity fileDescription = new FileDescriptionEntity();
                fileDescription.setGuid(file.getCanonicalFile().getAbsolutePath());
                fileDescription.setName(file.getName());
                // set is directory true/false
                fileDescription.setIsDirectory(file.isDirectory());

                // set file size
                if (!fileDescription.isIsDirectory()) {
                    fileDescription.setSize(file.length());
                }

                // add object to array list
                filesList.add(fileDescription);
            }
        }
        return filesList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadDocumentEntity loadDocument(LoadDocumentRequest loadDocumentRequest, boolean loadAllPages, boolean printVersion) {
        // set request parameters
        String documentGuid = loadDocumentRequest.getGuid();
        String password = loadDocumentRequest.getPassword();
        password = org.apache.commons.lang3.StringUtils.isEmpty(password) ? null : password;
        LoadDocumentEntity loadDocumentEntity;

        String fileCacheSubFolder = createFileCacheSubFolderPath(documentGuid);
        ViewerCache cache = null;
        if (viewerConfiguration.isCache()) {
            cache = new FileViewerCache(mCachePath, fileCacheSubFolder);
        }

        try (CustomViewer<?> customViewer = createCustomViewer(documentGuid, password, cache)) {

            loadDocumentEntity = getLoadDocumentEntity(loadAllPages, documentGuid, fileCacheSubFolder, customViewer);
            loadDocumentEntity.setShowGridLines(viewerConfiguration.getShowGridLines());
            loadDocumentEntity.setPrintAllowed(viewerConfiguration.getPrintAllowed());
        } catch (IncorrectPasswordException | PasswordRequiredException ex) {
            logger.error("Exception that is connected to password", ex);
            throw new TotalGroupDocsException(Utils.getExceptionMessage(password), ex);
        }
        return loadDocumentEntity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageDescriptionEntity loadDocumentPage(LoadDocumentPageRequest loadDocumentPageRequest) {
        String documentGuid = loadDocumentPageRequest.getGuid();
        Integer pageNumber = loadDocumentPageRequest.getPage();
        String password = loadDocumentPageRequest.getPassword();
        password = org.apache.commons.lang3.StringUtils.isEmpty(password) ? null : password;

        String fileCacheSubFolder = createFileCacheSubFolderPath(documentGuid);
        ViewerCache cache = new FileViewerCache(mCachePath, fileCacheSubFolder);

        try (CustomViewer<?> customViewer = createCustomViewer(documentGuid, password, cache)) {
            return getPageDescriptionEntity(customViewer, documentGuid, pageNumber, fileCacheSubFolder);
        } catch (Exception ex) {
            logger.error("Exception in loading document page", ex);
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageDescriptionEntity rotateDocumentPages(RotateDocumentPagesRequest rotateDocumentPagesRequest) {
        // set request parameters
        String documentGuid = rotateDocumentPagesRequest.getGuid();
        List<Integer> pages = rotateDocumentPagesRequest.getPages();
        String password = rotateDocumentPagesRequest.getPassword();
        Integer angle = rotateDocumentPagesRequest.getAngle();
        int pageNumber = pages.get(0);

        try {
            String fileCacheSubFolder = createFileCacheSubFolderPath(documentGuid);
            // Getting new rotation angle value.
            int currentAngle = PagesInfoStorage.loadPageAngle(fileCacheSubFolder, pageNumber);
            int newAngle = mergeAngles(currentAngle, angle);
            PagesInfoStorage.savePageAngle(fileCacheSubFolder, pageNumber, newAngle);
            // Generate new cache
            ViewerCache cache = null;
            if (viewerConfiguration.isCache()) {
                cache = new FileViewerCache(mCachePath, fileCacheSubFolder);
            }

            try (CustomViewer<?> customViewer = createCustomViewer(documentGuid, password, cache, pageNumber, newAngle)) {
                if (viewerConfiguration.isCache()) {
                    // Delete cache files connected to the page
                    customViewer.clearCache(pageNumber);
                }

                return getPageDescriptionEntity(customViewer, documentGuid, pageNumber, fileCacheSubFolder);
            }
        } catch (Exception ex) {
            logger.error("Exception in rotating document page", ex);
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }

    private String createFileCacheSubFolderPath(String documentGuid) {
        String fileFolderName = new File(documentGuid).getName().replace(".", "_");
        return mCachePath.resolve(fileFolderName).toAbsolutePath().toString();
    }

    @Override
    public ResponseEntity<byte[]> getResource(String guid, String resourceName) {
        try {
            if (!org.apache.commons.lang3.StringUtils.isEmpty(guid)) {
                final Path resourceFile = ViewerUtils.makeResourcesDir(viewerConfiguration).resolve(guid).resolve(resourceName);

                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setContentType(ViewerUtils.detectMediaType(resourceFile.getFileName().toString()));
                responseHeaders.setContentDisposition(
                        ContentDisposition.builder("inline")
                                .filename(resourceFile.toAbsolutePath().toString())
                                .build()
                );

                return ResponseEntity.ok()
                        .headers(responseHeaders)
                        .body(FileUtils.readFileToByteArray(resourceFile.toFile()));
            }
        } catch (IOException e) {
            logger.error("Exception in loading resource", e);
            throw new TotalGroupDocsException(e.getMessage(), e);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewerConfiguration getViewerConfiguration() {
        return viewerConfiguration;
    }

    @Override
    public InputStream getPdf(LoadDocumentRequest loadDocumentRequest) {
        String documentGuid = loadDocumentRequest.getGuid();
        String password = loadDocumentRequest.getPassword();
        password = org.apache.commons.lang3.StringUtils.isEmpty(password) ? null : password;

        String fileCacheSubFolder = createFileCacheSubFolderPath(documentGuid);
        // Generate new cache
        ViewerCache cache = null;
        if (viewerConfiguration.isCache()) {
            cache = new FileViewerCache(mCachePath, fileCacheSubFolder);
        }

        try (CustomViewer<?> customViewer = createCustomViewer(documentGuid, password, cache)) {
            return customViewer.getPdf();
        } catch (Exception ex) {
            logger.error("Exception in loading pdf file", ex);
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }

    private PageDescriptionEntity getPageDescriptionEntity(CustomViewer<?> customViewer, String documentGuid, int pageNumber, String fileCacheSubFolder) {
        ViewInfo viewInfo = customViewer.getViewInfo();
        PageDescriptionEntity page = getPageInfo(viewInfo.getPages().get(pageNumber - 1), fileCacheSubFolder);
        page.setData(customViewer.getPageContent(pageNumber));

        return page;
    }

    private LoadDocumentEntity getLoadDocumentEntity(boolean loadAllPages, String documentGuid, String fileCacheSubFolder, CustomViewer<?> customViewer) {
        try {
            ViewInfo viewInfo = customViewer.getViewInfo();

            LoadDocumentEntity loadDocumentEntity = new LoadDocumentEntity();

            String pagesInfoPath = PagesInfoStorage.createPagesInfo(fileCacheSubFolder, viewInfo, isViewerLicenseSet);

            List<Page> pages = viewInfo.getPages();
            for (int i = 0, pagesSize = pages.size(); i < pagesSize; i++) {
                if (!isViewerLicenseSet && i == 2) {
                    break; // only 2 pages in evaluation mode
                }
                Page page = pages.get(i);
                PageDescriptionEntity pageData = getPageInfo(page, pagesInfoPath);
                if (loadAllPages) {
                    pageData.setData(customViewer.getPageContent(page.getNumber()));
                }

                loadDocumentEntity.getPages().add(pageData);
            }

            loadDocumentEntity.setGuid(documentGuid);
            return loadDocumentEntity;
        } catch (Exception e) {
            throw new ReadWriteException(e);
        }
    }

    private void setLicense() {
        try {
            // set GroupDocs license
            License license = new License();
            license.setLicense(globalConfiguration.getApplication().getLicensePath());
        } catch (Throwable throwable) {
            logger.error("Can not verify Viewer license!");
        }
    }

    private CustomViewer<?> createCustomViewer(String documentGuid, String password, ViewerCache viewerCache) {
        return createCustomViewer(documentGuid, password, viewerCache, -1, 0);
    }

    private CustomViewer<?> createCustomViewer(String documentGuid, String password, ViewerCache viewerCache, int pageNumber, int newAngle) {

        CustomViewer<?> customViewer;
        if (viewerConfiguration.isHtmlMode()) {
            customViewer = new HtmlViewer(documentGuid, password, viewerCache, viewerConfiguration, pageNumber, newAngle);
        } else {
            customViewer = new PngViewer(documentGuid, password, viewerCache, viewerConfiguration, pageNumber, newAngle);
        }
        return customViewer;
    }
}
