package com.groupdocs.total.examples.developerguide.search;

import com.groupdocs.search.Index;
import com.groupdocs.search.options.SearchOptions;
import com.groupdocs.search.options.TableDiscreteFunction;
import com.groupdocs.search.results.SearchResult;

public class SearchFuzzy {
    public static void run() throws Exception {
        try (Index index = new Index("search-fuzzy/index")) {
            index.add("archive");

            SearchOptions options = new SearchOptions();
            options.getFuzzySearch().setEnabled(true);
            options.getFuzzySearch().setFuzzyAlgorithm(new TableDiscreteFunction(3));

            // Matches "throughput" even when spelled "througput" or "throughtput"
            SearchResult result = index.search("throughput", options);

            System.out.println("Documents found: " + result.getDocumentCount());
            System.out.println("Total occurrences: " + result.getOccurrenceCount());
        }
    }
}
