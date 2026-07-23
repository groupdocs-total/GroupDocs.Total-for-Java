package com.groupdocs.total.examples.developerguide.search;

import com.groupdocs.search.Index;
import com.groupdocs.search.results.FoundDocument;
import com.groupdocs.search.results.SearchResult;
import java.io.File;

public class SearchBoolean {
    public static void run() throws Exception {
        try (Index index = new Index("search-boolean/index")) {
            index.add("archive");

            SearchResult result = index.search("throughput AND NOT forecast");

            System.out.println("Documents found: " + result.getDocumentCount());

            for (int i = 0; i < result.getDocumentCount(); i++) {
                FoundDocument document = result.getFoundDocument(i);
                System.out.println("  " + new File(document.getDocumentInfo().getFilePath()).getName());
            }
        }
    }
}
