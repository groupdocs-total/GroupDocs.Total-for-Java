package com.groupdocs.total.examples.developerguide.viewer;

import com.groupdocs.total.examples.ExampleFiles;
import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.PdfViewOptions;

public class ViewerRenderToPdf {
    public static void run() throws Exception {
        try (Viewer viewer = new Viewer(ExampleFiles.path("contract.docx"))) {
            PdfViewOptions viewOptions = new PdfViewOptions(ExampleFiles.output("viewer-render-to-pdf.pdf"));

            viewer.view(viewOptions);
        }
    }
}
