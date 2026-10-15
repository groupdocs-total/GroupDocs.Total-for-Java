package com.groupdocs.total.examples.developerguide.merger;

import com.groupdocs.merger.Merger;
import com.groupdocs.merger.domain.options.SplitMode;
import com.groupdocs.merger.domain.options.SplitOptions;
import com.groupdocs.total.examples.ExampleFiles;

public class MergerSplitPages {
    public static void run() throws Exception {
        Merger merger = new Merger(ExampleFiles.path("contract.docx"));

        // {0} is the page number. contract.docx has 2 pages.
        int[] pageNumbers = new int[] { 1, 2 };
        SplitOptions splitOptions = new SplitOptions(
            ExampleFiles.output("merger-split-pages/page_{0}.docx"), pageNumbers, SplitMode.Pages);

        merger.split(splitOptions);
    }
}
