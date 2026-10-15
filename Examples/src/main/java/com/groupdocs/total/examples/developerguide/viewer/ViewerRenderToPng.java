package com.groupdocs.total.examples.developerguide.viewer;

import com.groupdocs.total.examples.ExampleFiles;
import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.PngViewOptions;

public class ViewerRenderToPng {
    public static void run() throws Exception {
        try (Viewer viewer = new Viewer(ExampleFiles.path("contract.docx"))) {
            PngViewOptions viewOptions = new PngViewOptions(ExampleFiles.output("viewer-render-to-png/page_{0}.png"));

            // Pages 1 and 2
            viewer.view(viewOptions, 1, 2);
        }
    }
}
