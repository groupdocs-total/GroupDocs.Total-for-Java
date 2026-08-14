package com.groupdocs.total.examples.developerguide.redaction;

import com.groupdocs.redaction.RedactionStatus;
import com.groupdocs.redaction.Redactor;
import com.groupdocs.redaction.RedactorChangeLog;
import com.groupdocs.redaction.options.RasterizationOptions;
import com.groupdocs.redaction.redactions.ExactPhraseRedaction;
import com.groupdocs.redaction.redactions.ReplacementOptions;
import com.groupdocs.total.examples.ExampleFiles;
import java.io.FileOutputStream;

public class RedactionExactPhrase {
    public static void run() throws Exception {
        try (Redactor redactor = new Redactor(ExampleFiles.path("contract.docx"))) {
            RedactorChangeLog result = redactor.apply(
                new ExactPhraseRedaction("Project Northlight", new ReplacementOptions("[REDACTED]")));

            System.out.println("Status: " + result.getStatus());

            if (result.getStatus() != RedactionStatus.Failed) {
                // save() with no path overwrites the source, so write to a new file via a stream.
                // Rasterization stays off to keep the result an editable DOCX.
                RasterizationOptions rasterizationOptions = new RasterizationOptions();
                rasterizationOptions.setEnabled(false);
                try (FileOutputStream output = new FileOutputStream(ExampleFiles.output("redaction-exact-phrase.docx"))) {
                    redactor.save(output, rasterizationOptions);
                }
            }
        }
    }
}
