package com.groupdocs.ui.viewer.viewer;

import com.groupdocs.ui.viewer.config.ViewerConfiguration;
import com.groupdocs.ui.viewer.util.ViewerUtils;
import com.groupdocs.viewer.options.HtmlViewOptions;
import com.groupdocs.viewer.options.Rotation;
import com.groupdocs.viewer.options.TextOverflowMode;
import com.groupdocs.viewer.options.ViewInfoOptions;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HtmlViewer extends CustomViewer<HtmlViewOptions> {

    private CustomPageStreamFactory mPageStreamFactory;
    private CustomResourceStreamFactory mResourceStreamFactory;

    public HtmlViewer(String filePath, String password, ViewerConfiguration viewerConfiguration) {
        this(filePath, password, viewerConfiguration, -1, 0);
    }

    public HtmlViewer(String filePath, String password, ViewerConfiguration viewerConfiguration, int pageNumber/* = -1*/, int newAngle/* = 0*/) {
        super(filePath, viewerConfiguration, password);
        this.viewOptions = this.createHtmlViewOptions(filePath, viewerConfiguration, pageNumber, newAngle);
        this.viewInfoOptions = ViewInfoOptions.fromHtmlViewOptions(this.viewOptions);
    }

    @Override
    public String getPageContent(int pageNumber) {
        viewer.view(this.viewOptions, pageNumber);
        try {
            final byte[] pageContent = mPageStreamFactory.getPageContent(pageNumber);
            return new String(pageContent, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private com.groupdocs.viewer.options.HtmlViewOptions createHtmlViewOptions(String documentGuid, ViewerConfiguration viewerConfiguration, int passedPageNumber/* = -1*/, int newAngle/* = 0*/) {
        final Path resourcesDir = ViewerUtils.makeResourcesDir(viewerConfiguration);
        final String subFolder = ViewerUtils.replaceChars(Paths.get(documentGuid).getFileName().toString());
        mPageStreamFactory = new CustomPageStreamFactory(resourcesDir.resolve(subFolder), ".html");
        mResourceStreamFactory = new CustomResourceStreamFactory(resourcesDir, subFolder);
        HtmlViewOptions htmlViewOptions = HtmlViewOptions.forExternalResources(mPageStreamFactory, mResourceStreamFactory);

        htmlViewOptions.getSpreadsheetOptions().setTextOverflowMode(TextOverflowMode.HIDE_TEXT);
        htmlViewOptions.getSpreadsheetOptions().setSkipEmptyColumns(true);
        htmlViewOptions.getSpreadsheetOptions().setSkipEmptyRows(true);
        setWatermarkOptions(htmlViewOptions, viewerConfiguration.getWatermarkText());

        if (passedPageNumber >= 0 && newAngle != 0) {
            Rotation rotationAngle = getRotationByAngle(newAngle);
            htmlViewOptions.rotatePage(passedPageNumber, rotationAngle);
        }

        return htmlViewOptions;
    }
}
