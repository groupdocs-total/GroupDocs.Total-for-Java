package com.groupdocs.total.examples.developerguide.annotation;

import com.groupdocs.annotation.Annotator;
import com.groupdocs.annotation.models.Rectangle;
import com.groupdocs.annotation.models.annotationmodels.AreaAnnotation;

public class AnnotationAddArea {
    public static void run() throws Exception {
        try (Annotator annotator = new Annotator("contract.docx")) {
            AreaAnnotation area = new AreaAnnotation();
            area.setBox(new Rectangle(100, 100, 200, 100));
            area.setBackgroundColor(65535);
            area.setMessage("Please confirm these figures");
            area.setPageNumber(0);
            area.setOpacity(0.7);

            annotator.add(area);
            annotator.save("annotation-add-area.pdf");
        }
    }
}
