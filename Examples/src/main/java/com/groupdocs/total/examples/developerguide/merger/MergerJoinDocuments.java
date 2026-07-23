package com.groupdocs.total.examples.developerguide.merger;

import com.groupdocs.merger.Merger;

public class MergerJoinDocuments {
    public static void run() throws Exception {
        Merger merger = new Merger("contract.docx");

        merger.join("statement-of-work.docx");

        // Merger.save resolves a bare relative name oddly, so pass an absolute path
        merger.save(new java.io.File("merger-join-documents.docx").getAbsolutePath());
    }
}
