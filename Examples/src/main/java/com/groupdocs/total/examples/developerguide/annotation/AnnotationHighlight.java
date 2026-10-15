package com.groupdocs.total.examples.developerguide.annotation;

import com.groupdocs.annotation.Annotator;
import com.groupdocs.annotation.models.Point;
import com.groupdocs.annotation.models.annotationmodels.HighlightAnnotation;
import com.groupdocs.total.examples.ExampleFiles;
import java.util.Arrays;
import java.util.List;

public class AnnotationHighlight {
    public static void run() throws Exception {
        try (Annotator annotator = new Annotator(ExampleFiles.path("contract.docx"))) {
            // Highlighted area
            List<Point> points = Arrays.asList(
                new Point(80, 730),
                new Point(240, 730),
                new Point(80, 710),
                new Point(240, 710));

            HighlightAnnotation highlight = new HighlightAnnotation();
            highlight.setPoints(points);
            highlight.setBackgroundColor(65535);
            highlight.setMessage("Check this clause against the contract");
            highlight.setPageNumber(0);
            highlight.setOpacity(0.5);

            annotator.add(highlight);
            annotator.save(ExampleFiles.output("annotation-highlight.pdf"));
        }
    }
}
