package com.groupdocs.total.examples.developerguide.annotation;

import com.groupdocs.annotation.Annotator;
import com.groupdocs.annotation.models.Rectangle;
import com.groupdocs.annotation.models.annotationmodels.AnnotationBase;
import com.groupdocs.annotation.models.annotationmodels.AreaAnnotation;
import com.groupdocs.total.examples.ExampleFiles;
import java.util.List;

public class AnnotationExtract {
    public static void run() throws Exception {
        // Annotate first, so there is something to read back
        try (Annotator annotator = new Annotator(ExampleFiles.path("contract.docx"))) {
            AreaAnnotation area = new AreaAnnotation();
            area.setBox(new Rectangle(100, 100, 200, 100));
            area.setMessage("Please confirm these figures");
            area.setPageNumber(0);

            annotator.add(area);
            annotator.save(ExampleFiles.output("annotation-extract.pdf"));
        }

        try (Annotator annotator = new Annotator(ExampleFiles.output("annotation-extract.pdf"))) {
            List<AnnotationBase> annotations = annotator.get();

            System.out.println("Annotations found: " + annotations.size());

            for (AnnotationBase annotation : annotations) {
                System.out.println(annotation.getClass().getSimpleName()
                    + " on page " + annotation.getPageNumber()
                    + ": " + annotation.getMessage());
            }
        }
    }
}
