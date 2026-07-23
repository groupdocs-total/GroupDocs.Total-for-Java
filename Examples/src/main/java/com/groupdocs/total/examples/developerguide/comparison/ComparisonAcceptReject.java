package com.groupdocs.total.examples.developerguide.comparison;

import com.groupdocs.comparison.Comparer;
import com.groupdocs.comparison.options.ApplyChangeOptions;
import com.groupdocs.comparison.result.ChangeInfo;
import com.groupdocs.comparison.result.ComparisonAction;

public class ComparisonAcceptReject {
    public static void run() throws Exception {
        try (Comparer comparer = new Comparer("contract-v1.docx")) {
            comparer.add("contract-v2.docx");
            comparer.compare();

            ChangeInfo[] changes = comparer.getChanges();

            // Accept everything except the first change, which we reject
            for (ChangeInfo change : changes) {
                change.setComparisonAction(ComparisonAction.ACCEPT);
            }
            if (changes.length > 0) {
                changes[0].setComparisonAction(ComparisonAction.REJECT);
                System.out.println("Rejected: '" + changes[0].getTargetText() + "'");
            }

            ApplyChangeOptions applyOptions = new ApplyChangeOptions();
            applyOptions.setChanges(changes);

            comparer.applyChanges("comparison-accept-reject.docx", applyOptions);
        }
    }
}
