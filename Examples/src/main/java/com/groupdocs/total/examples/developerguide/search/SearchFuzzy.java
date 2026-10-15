package com.groupdocs.total.examples.developerguide.search;

import com.groupdocs.search.Index;
import com.groupdocs.search.options.SearchOptions;
import com.groupdocs.search.options.TableDiscreteFunction;
import com.groupdocs.search.results.SearchResult;
import com.groupdocs.total.examples.ExampleFiles;

public class SearchFuzzy {
    public static void run() throws Exception {
        try (Index index = new Index(ExampleFiles.output("search-fuzzy/index"))) {
            index.add(ExampleFiles.path("archive"));

            SearchOptions options = new SearchOptions();
            options.getFuzzySearch().setEnabled(true);
            options.getFuzzySearch().setFuzzyAlgorithm(new TableDiscreteFunction(3));

            // Also matches misspellings
            SearchResult result = index.search("throughput", options);

            System.out.println("Documents found: " + result.getDocumentCount());
            System.out.println("Total occurrences: " + result.getOccurrenceCount());
        }
    }
}
