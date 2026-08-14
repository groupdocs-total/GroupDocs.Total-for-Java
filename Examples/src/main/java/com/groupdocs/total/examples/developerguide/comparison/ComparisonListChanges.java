package com.groupdocs.total.examples.developerguide.comparison;

import com.groupdocs.comparison.Comparer;
import com.groupdocs.comparison.result.ChangeInfo;
import com.groupdocs.total.examples.ExampleFiles;

public class ComparisonListChanges {
    public static void run() throws Exception {
        try (Comparer comparer = new Comparer(ExampleFiles.path("contract-v1.docx"))) {
            comparer.add(ExampleFiles.path("contract-v2.docx"));
            comparer.compare();

            ChangeInfo[] changes = comparer.getChanges();

            System.out.println("Changes found: " + changes.length);

            for (ChangeInfo change : changes) {
                System.out.println(change.getId() + ". " + change.getType()
                        + " | source: '" + change.getSourceText() + "'"
                        + " | target: '" + change.getTargetText() + "'");
            }
        }
    }
}
