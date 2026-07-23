package com.groupdocs.total.examples.developerguide.merger;

import com.groupdocs.merger.Merger;
import com.groupdocs.merger.domain.result.IDocumentInfo;

public class MergerDocumentInfo {
    public static void run() throws Exception {
        Merger merger = new Merger("contract.docx");

        IDocumentInfo info = merger.getDocumentInfo();

        System.out.println("File type: " + info.getType().getFileFormat());
        System.out.println("Pages: " + info.getPageCount());
        System.out.println("Size: " + info.getSize() + " bytes");
    }
}
