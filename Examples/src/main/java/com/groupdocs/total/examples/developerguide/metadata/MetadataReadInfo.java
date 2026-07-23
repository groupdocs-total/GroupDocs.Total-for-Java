package com.groupdocs.total.examples.developerguide.metadata;

import com.groupdocs.metadata.Metadata;
import com.groupdocs.metadata.core.IDocumentInfo;

public class MetadataReadInfo {
    public static void run() throws Exception {
        try (Metadata metadata = new Metadata("contract.docx")) {
            IDocumentInfo info = metadata.getDocumentInfo();

            System.out.println("File format: " + info.getFileType().getFileFormat());
            System.out.println("Extension: " + info.getFileType().getExtension());
            System.out.println("MIME type: " + info.getFileType().getMimeType());
            System.out.println("Pages: " + info.getPageCount());
            System.out.println("Size: " + info.getSize() + " bytes");
            System.out.println("Encrypted: " + info.isEncrypted());
        }
    }
}
