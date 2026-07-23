package com.groupdocs.total.examples.developerguide.viewer;

import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.PdfViewOptions;

public class ViewerRenderToPdf {
    public static void run() throws Exception {
        try (Viewer viewer = new Viewer("contract.docx")) {
            PdfViewOptions viewOptions = new PdfViewOptions("viewer-render-to-pdf.pdf");

            viewer.view(viewOptions);
        }
    }
}
