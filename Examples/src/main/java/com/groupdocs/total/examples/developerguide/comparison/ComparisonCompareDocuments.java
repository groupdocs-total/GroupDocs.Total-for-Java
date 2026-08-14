package com.groupdocs.total.examples.developerguide.comparison;

import com.groupdocs.comparison.Comparer;
import com.groupdocs.total.examples.ExampleFiles;

public class ComparisonCompareDocuments {
    public static void run() throws Exception {
        try (Comparer comparer = new Comparer(ExampleFiles.path("contract-v1.docx"))) {
            comparer.add(ExampleFiles.path("contract-v2.docx"));

            comparer.compare(ExampleFiles.output("comparison-compare-documents.docx"));
        }
    }
}
