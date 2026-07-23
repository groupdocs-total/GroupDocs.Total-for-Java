package com.groupdocs.total.examples.developerguide.redaction;

import com.groupdocs.redaction.RedactionStatus;
import com.groupdocs.redaction.Redactor;
import com.groupdocs.redaction.RedactorChangeLog;
import com.groupdocs.redaction.options.RasterizationOptions;
import com.groupdocs.redaction.redactions.RegexRedaction;
import com.groupdocs.redaction.redactions.ReplacementOptions;
import java.io.FileOutputStream;

public class RedactionRegex {
    public static void run() throws Exception {
        try (Redactor redactor = new Redactor("contract.docx")) {
            // Any currency amount such as "120,000 USD"
            RedactorChangeLog result = redactor.apply(
                new RegexRedaction("\\d{1,3}(,\\d{3})*\\s?USD", new ReplacementOptions("[AMOUNT]")));

            System.out.println("Status: " + result.getStatus());

            if (result.getStatus() != RedactionStatus.Failed) {
                RasterizationOptions rasterizationOptions = new RasterizationOptions();
                rasterizationOptions.setEnabled(false);
                try (FileOutputStream output = new FileOutputStream("redaction-regex.docx")) {
                    redactor.save(output, rasterizationOptions);
                }
            }
        }
    }
}
