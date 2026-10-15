package com.groupdocs.total.examples.developerguide.viewer;

import com.groupdocs.total.examples.ExampleFiles;
import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.HtmlViewOptions;

public class ViewerRenderToHtml {
    public static void run() throws Exception {
        try (Viewer viewer = new Viewer(ExampleFiles.path("contract.docx"))) {
            // {0} is the page number
            HtmlViewOptions viewOptions = HtmlViewOptions.forEmbeddedResources(
                ExampleFiles.output("viewer-render-to-html/page_{0}.html"));

            viewer.view(viewOptions);
        }
    }
}
