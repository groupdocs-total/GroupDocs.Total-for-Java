package com.groupdocs.total.examples.developerguide.viewer;

import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.HtmlViewOptions;

public class ViewerRenderToHtml {
    public static void run() throws Exception {
        try (Viewer viewer = new Viewer("contract.docx")) {
            // {0} is replaced with the page number, so page 1 becomes page_1.html
            HtmlViewOptions viewOptions = HtmlViewOptions.forEmbeddedResources(
                "viewer-render-to-html/page_{0}.html");

            viewer.view(viewOptions);
        }
    }
}
