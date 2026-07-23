package com.groupdocs.total.examples.developerguide.merger;

import com.groupdocs.merger.Merger;
import com.groupdocs.merger.domain.options.SplitMode;
import com.groupdocs.merger.domain.options.SplitOptions;

public class MergerSplitPages {
    public static void run() throws Exception {
        Merger merger = new Merger("contract.docx");

        // {0} is replaced with the page number, so page 1 becomes page_1.docx
        int[] pageNumbers = new int[] { 1, 2, 3 };
        SplitOptions splitOptions = new SplitOptions(
            "merger-split-pages/page_{0}.docx", pageNumbers, SplitMode.Pages);

        merger.split(splitOptions);
    }
}
