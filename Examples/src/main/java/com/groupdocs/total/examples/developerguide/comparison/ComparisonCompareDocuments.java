package com.groupdocs.total.examples.developerguide.comparison;

import com.groupdocs.comparison.Comparer;

public class ComparisonCompareDocuments {
    public static void run() throws Exception {
        try (Comparer comparer = new Comparer("contract-v1.docx")) {
            comparer.add("contract-v2.docx");

            comparer.compare("comparison-compare-documents.docx");
        }
    }
}
