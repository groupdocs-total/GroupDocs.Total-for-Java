package com.groupdocs.ui.viewer.viewer;

import com.groupdocs.ui.viewer.cache.ViewerCache;
import com.groupdocs.ui.viewer.cache.model.*;
import com.groupdocs.ui.viewer.config.ViewerConfiguration;
import com.groupdocs.ui.viewer.util.ViewerUtils;
import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.*;
import com.groupdocs.viewer.results.ViewInfo;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class CustomViewer<T extends ViewOptions> implements Closeable {
    private static final Class<?>[] DESERIALIZATION_CLASSES = new Class[]{
            ArchiveViewInfoModel.class,
            AttachmentModel.class,
            CadViewInfoModel.class,
            CharacterModel.class,
            FileInfoModel.class,
            LayerModel.class,
            LayoutModel.class,
            LineModel.class,
            LotusNotesViewInfoModel.class,
            OutlookViewInfoModel.class,
            PageModel.class,
            PdfViewInfoModel.class,
            ProjectManagementViewInfoModel.class,
            ViewInfoModel.class,
            WordModel.class
    };
    protected final String filePath;
    protected final Viewer viewer;
    protected final ViewerConfiguration viewerConfiguration;
    protected ViewInfoOptions viewInfoOptions;
    protected T viewOptions;
    private PdfViewOptions pdfViewOptions;
    private ViewerCache mCache;

    public CustomViewer(String filePath, ViewerCache viewerCache, ViewerConfiguration viewerConfiguration, String password) {
        this.filePath = filePath;
        this.viewerConfiguration = viewerConfiguration;
        this.mCache = viewerCache;
        this.viewer = new Viewer(filePath, createLoadOptions(password));
        this.pdfViewOptions = createPdfViewOptions();
    }

    /**
     * Gets enumeration member by rotation angle value.
     *
     * @param newAngle New rotation angle value.
     * @return Rotation enumeration member.
     */
    protected static Rotation getRotationByAngle(int newAngle) {
        switch (newAngle) {
            case 90:
                return Rotation.ON_90_DEGREE;
            case 180:
                return Rotation.ON_180_DEGREE;
            case 270:
                return Rotation.ON_270_DEGREE;
        }
        return Rotation.ON_90_DEGREE;
    }

    protected static LoadOptions createLoadOptions(String password) {
        final LoadOptions loadOptions = new LoadOptions();
        loadOptions.setResourceLoadingTimeout(500);
        if (password != null && !Strings.isEmpty(password)) {
            loadOptions.setPassword(password);
        }
        return loadOptions;
    }

    /**
     * Adds watermark on document if its specified in configuration file.
     *
     * @param options View options.
     */
    protected void setWatermarkOptions(ViewOptions options, String watermarkText) {
        Watermark watermark = null;

        if (watermarkText != null && !watermarkText.isEmpty()) {
            // Set watermark properties
            watermark = new Watermark(watermarkText);
            watermark.setColor(Color.BLUE);
            watermark.setPosition(Position.DIAGONAL);
        }

        if (watermark != null) {
            options.setWatermark(watermark);
        }
    }

    public ViewInfo getViewInfo() {
        if (viewerConfiguration.isCache()) {
            String cacheKey = "view_info.dat";

            if (mCache.doesNotContains(cacheKey)) {
                synchronized (filePath) {
                    if (mCache.doesNotContains(cacheKey)) {
                        return mCache.get(cacheKey, this.readViewInfo(viewInfoOptions), DESERIALIZATION_CLASSES);
                    }
                }
            }

            return mCache.get(cacheKey, null, DESERIALIZATION_CLASSES);
        } else {
            return this.readViewInfo(viewInfoOptions);
        }
    }

    private ViewInfo readViewInfo(ViewInfoOptions viewInfoOptions) {
        return getViewer().getViewInfo(viewInfoOptions);
    }

    public Viewer getViewer() {
        return this.viewer;
    }

    public InputStream getPdf() {
        final Path resourcesDir = ViewerUtils.makeResourcesDir(viewerConfiguration);
        final String subFolder = ViewerUtils.replaceChars(Paths.get(filePath).getFileName().toString());
        final Path resourcesFileDir = resourcesDir.resolve(subFolder);
        final Path pdfFilePath = resourcesFileDir.resolve("f.pdf");

        final File pdfFile = pdfFilePath.toFile();
        if (!pdfFile.exists()) {
            this.viewer.view(this.pdfViewOptions);
        }

        try {
            byte[] bytes = FileUtils.readFileToByteArray(pdfFile);
            return new ByteArrayInputStream(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        this.viewer.close();
    }

    public abstract String getPageContent(int pageNumber);

    public void clearCache(int pageNumber) {
        if (mCache != null) {
            mCache.clearCache(pageNumber);
        }
    }

    private com.groupdocs.viewer.options.PdfViewOptions createPdfViewOptions() {
        final Path resourcesDir = ViewerUtils.makeResourcesDir(viewerConfiguration);
        final String subFolder = ViewerUtils.replaceChars(Paths.get(filePath).getFileName().toString());
        PdfViewOptions pdfViewOptions = new PdfViewOptions(
                new CustomFileStreamFactory(resourcesDir.resolve(subFolder), ".pdf"));
        setWatermarkOptions(pdfViewOptions, viewerConfiguration.getWatermarkText());
        return pdfViewOptions;
    }

    public ViewerCache getCache() {
        return mCache;
    }
}
