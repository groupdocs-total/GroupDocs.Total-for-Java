package com.groupdocs.total.examples.developerguide.comparison;

import com.groupdocs.comparison.Comparer;
import com.groupdocs.comparison.options.ApplyChangeOptions;
import com.groupdocs.comparison.result.ChangeInfo;
import com.groupdocs.comparison.result.ComparisonAction;
import com.groupdocs.total.examples.ExampleFiles;

public class ComparisonAcceptReject {
    public static void run() throws Exception {
        try (Comparer comparer = new Comparer(ExampleFiles.path("contract-v1.docx"))) {
            comparer.add(ExampleFiles.path("contract-v2.docx"));
            comparer.compare();

            ChangeInfo[] changes = comparer.getChanges();

            // Accept all changes except the first
            for (ChangeInfo change : changes) {
                change.setComparisonAction(ComparisonAction.ACCEPT);
            }
            if (changes.length > 0) {
                changes[0].setComparisonAction(ComparisonAction.REJECT);
                System.out.println("Rejected: '" + changes[0].getTargetText() + "'");
            }

            ApplyChangeOptions applyOptions = new ApplyChangeOptions();
            applyOptions.setChanges(changes);

            comparer.applyChanges(ExampleFiles.output("comparison-accept-reject.docx"), applyOptions);
        }
    }
}
