package com.groupdocs.total.examples.developerguide.merger;

import com.groupdocs.merger.Merger;
import com.groupdocs.total.examples.ExampleFiles;

public class MergerJoinDocuments {
    public static void run() throws Exception {
        Merger merger = new Merger(ExampleFiles.path("contract.docx"));

        merger.join(ExampleFiles.path("statement-of-work.docx"));

        merger.save(ExampleFiles.output("merger-join-documents.docx"));
    }
}
