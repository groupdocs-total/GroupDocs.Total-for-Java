package com.groupdocs.total.examples.developerguide.redaction;

import com.groupdocs.redaction.RedactionStatus;
import com.groupdocs.redaction.Redactor;
import com.groupdocs.redaction.RedactorChangeLog;
import com.groupdocs.redaction.options.RasterizationOptions;
import com.groupdocs.redaction.redactions.EraseMetadataRedaction;
import com.groupdocs.redaction.redactions.MetadataFilters;
import com.groupdocs.total.examples.ExampleFiles;
import java.io.FileOutputStream;

public class RedactionMetadata {
    public static void run() throws Exception {
        try (Redactor redactor = new Redactor(ExampleFiles.path("contract.docx"))) {
            RedactorChangeLog result = redactor.apply(
                new EraseMetadataRedaction(MetadataFilters.All));

            System.out.println("Status: " + result.getStatus());

            if (result.getStatus() != RedactionStatus.Failed) {
                RasterizationOptions rasterizationOptions = new RasterizationOptions();
                rasterizationOptions.setEnabled(false);
                try (FileOutputStream output = new FileOutputStream(ExampleFiles.output("redaction-metadata.docx"))) {
                    redactor.save(output, rasterizationOptions);
                }
            }
        }
    }
}
