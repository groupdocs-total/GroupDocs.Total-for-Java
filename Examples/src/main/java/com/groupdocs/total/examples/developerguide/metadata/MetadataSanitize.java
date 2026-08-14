package com.groupdocs.total.examples.developerguide.metadata;

import com.groupdocs.metadata.Metadata;
import com.groupdocs.total.examples.ExampleFiles;

public class MetadataSanitize {
    public static void run() throws Exception {
        try (Metadata metadata = new Metadata(ExampleFiles.path("contract.docx"))) {
            int removed = metadata.sanitize();
            System.out.println("Properties removed: " + removed);

            metadata.save(ExampleFiles.output("metadata-sanitize.docx"));
        }
    }
}
