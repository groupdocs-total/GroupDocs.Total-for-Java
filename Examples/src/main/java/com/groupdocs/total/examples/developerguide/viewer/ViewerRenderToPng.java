package com.groupdocs.total.examples.developerguide.viewer;

import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.PngViewOptions;

public class ViewerRenderToPng {
    public static void run() throws Exception {
        try (Viewer viewer = new Viewer("contract.docx")) {
            PngViewOptions viewOptions = new PngViewOptions("viewer-render-to-png/page_{0}.png");

            // Render only the first two pages
            viewer.view(viewOptions, 1, 2);
        }
    }
}
