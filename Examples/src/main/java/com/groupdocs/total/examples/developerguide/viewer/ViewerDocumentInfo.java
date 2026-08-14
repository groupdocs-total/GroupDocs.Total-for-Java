package com.groupdocs.total.examples.developerguide.viewer;

import com.groupdocs.total.examples.ExampleFiles;
import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.ViewInfoOptions;
import com.groupdocs.viewer.results.Page;
import com.groupdocs.viewer.results.ViewInfo;

public class ViewerDocumentInfo {
    public static void run() throws Exception {
        try (Viewer viewer = new Viewer(ExampleFiles.path("contract.docx"))) {
            ViewInfoOptions viewInfoOptions = ViewInfoOptions.forHtmlView();
            ViewInfo viewInfo = viewer.getViewInfo(viewInfoOptions);

            System.out.println("File type: " + viewInfo.getFileType());
            System.out.println("Pages: " + viewInfo.getPages().size());

            for (Page page : viewInfo.getPages()) {
                System.out.println("Page " + page.getNumber() + ": " + page.getWidth() + "x" + page.getHeight());
            }
        }
    }
}
