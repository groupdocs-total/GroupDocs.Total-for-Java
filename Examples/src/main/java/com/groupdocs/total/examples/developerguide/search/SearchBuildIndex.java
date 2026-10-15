package com.groupdocs.total.examples.developerguide.search;

import com.groupdocs.search.Index;
import com.groupdocs.search.results.FoundDocument;
import com.groupdocs.search.results.SearchResult;
import com.groupdocs.total.examples.ExampleFiles;
import java.io.File;

public class SearchBuildIndex {
    public static void run() throws Exception {
        try (Index index = new Index(ExampleFiles.output("search-build-index/index"))) {
            index.add(ExampleFiles.path("archive"));

            SearchResult result = index.search("throughput");

            System.out.println("Documents found: " + result.getDocumentCount());
            System.out.println("Total occurrences: " + result.getOccurrenceCount());

            for (int i = 0; i < result.getDocumentCount(); i++) {
                FoundDocument document = result.getFoundDocument(i);
                String fileName = new File(document.getDocumentInfo().getFilePath()).getName();
                System.out.println("  " + fileName + " (" + document.getOccurrenceCount() + " occurrences)");
            }
        }
    }
}
