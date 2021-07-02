package com.groupdocs.ui.viewer.viewer;

import com.groupdocs.ui.viewer.config.ViewerConfiguration;
import com.groupdocs.ui.viewer.util.ViewerUtils;
import com.groupdocs.viewer.options.PngViewOptions;
import com.groupdocs.viewer.options.Rotation;
import com.groupdocs.viewer.options.ViewInfoOptions;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class PngViewer extends CustomViewer<PngViewOptions> {

    private CustomPageStreamFactory mPageStreamFactory;

    public PngViewer(String filePath, String password, ViewerConfiguration viewerConfiguration) {
        this(filePath, password, viewerConfiguration, -1, 0);
    }

    public PngViewer(String documentGuid, String password, ViewerConfiguration viewerConfiguration, int pageNumber/* = -1*/, int newAngle/* = 0*/) {
        super(documentGuid, viewerConfiguration, password);
        this.viewOptions = this.createPngViewOptions(documentGuid, viewerConfiguration, pageNumber, newAngle);
        this.viewInfoOptions = ViewInfoOptions.fromPngViewOptions(this.viewOptions);
    }

    @Override
    public String getPageContent(int pageNumber) {
        viewer.view(this.viewOptions, pageNumber);
        try {
            final byte[] pageContent = mPageStreamFactory.getPageContent(pageNumber);
            return Base64.getEncoder().encodeToString(pageContent);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private PngViewOptions createPngViewOptions(String documentGuid, ViewerConfiguration viewerConfiguration, int passedPageNumber/* = -1*/, int newAngle/* = 0*/) {
        final Path resourcesDir = ViewerUtils.makeResourcesDir(viewerConfiguration);
        final String subFolder = ViewerUtils.replaceChars(Paths.get(documentGuid).getFileName().toString());
        mPageStreamFactory = new CustomPageStreamFactory(resourcesDir.resolve(subFolder), ".png");
        PngViewOptions createdPngViewOptions = new PngViewOptions(mPageStreamFactory);

        if (passedPageNumber >= 0 && newAngle != 0) {
            Rotation rotationAngle = getRotationByAngle(newAngle);
            createdPngViewOptions.rotatePage(passedPageNumber, rotationAngle);
        }

        setWatermarkOptions(createdPngViewOptions, viewerConfiguration.getWatermarkText());

        return createdPngViewOptions;
    }
}
