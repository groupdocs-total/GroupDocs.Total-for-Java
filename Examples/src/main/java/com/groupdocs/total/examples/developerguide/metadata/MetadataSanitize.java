package com.groupdocs.total.examples.developerguide.metadata;

import com.groupdocs.metadata.Metadata;

public class MetadataSanitize {
    public static void run() throws Exception {
        try (Metadata metadata = new Metadata("contract.docx")) {
            int removed = metadata.sanitize();
            System.out.println("Properties removed: " + removed);

            metadata.save("metadata-sanitize.docx");
        }
    }
}
