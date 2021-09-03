package com.groupdocs.ui.viewer.viewer;

import com.groupdocs.ui.viewer.util.ViewerUtils;
import com.groupdocs.ui.viewer.cache.FileViewerCache;
import com.groupdocs.ui.viewer.cache.ViewerCache;
import com.groupdocs.ui.viewer.config.ViewerConfiguration;
import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.ViewerSettings;
import com.groupdocs.viewer.options.*;
import com.groupdocs.viewer.results.ViewInfo;
import org.apache.logging.log4j.util.Strings;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class CustomViewer<T extends ViewOptions> implements Closeable {
    protected final String filePath;
    protected final Viewer viewer;
    protected final ViewerConfiguration viewerConfiguration;
    protected ViewInfoOptions viewInfoOptions;
    private PdfViewOptions pdfViewOptions;
    protected T viewOptions;
    ViewerCache mCache;

    public CustomViewer(String filePath, ViewerConfiguration viewerConfiguration, String password) {
        this.filePath = filePath;
        this.viewerConfiguration = viewerConfiguration;
        if (viewerConfiguration.isCache()) {
            Path cacheDir = Paths.get(viewerConfiguration.getFilesDirectory(), viewerConfiguration.getCacheFolderName());
            // Create cache
            this.mCache = new FileViewerCache(cacheDir, filePath);
            this.viewer = new Viewer(filePath, createLoadOptions(password), new ViewerSettings(this.mCache));
        } else {
            this.viewer = new Viewer(filePath, createLoadOptions(password));
        }
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
        return viewer.getViewInfo(viewInfoOptions);
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
        if(!pdfFile.exists()) {
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

    public void close() {
        this.viewer.close();
    }

    public abstract String getPageContent(int pageNumber);

    public void clearCache(int pageNumber) {
        mCache.clearCache(pageNumber);
    }

    private com.groupdocs.viewer.options.PdfViewOptions createPdfViewOptions() {
        final Path resourcesDir = ViewerUtils.makeResourcesDir(viewerConfiguration);
        final String subFolder = ViewerUtils.replaceChars(Paths.get(filePath).getFileName().toString());
        PdfViewOptions pdfViewOptions = new PdfViewOptions(
            new CustomFileStreamFactory(resourcesDir.resolve(subFolder), ".pdf"));
        setWatermarkOptions(pdfViewOptions, viewerConfiguration.getWatermarkText());
        return pdfViewOptions;
    }

    protected static LoadOptions createLoadOptions(String password) {
        final LoadOptions loadOptions = new LoadOptions();
        if (password != null && !Strings.isEmpty(password)) {
            loadOptions.setPassword(password);
        }
        return loadOptions;
    }
}
