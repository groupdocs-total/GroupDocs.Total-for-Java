package com.groupdocs.total.examples.developerguide.conversion;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.PdfConvertOptions;

public class ConversionWordToPdf {
    public static void run() throws Exception {
        try (Converter converter = new Converter("contract.docx")) {
            PdfConvertOptions convertOptions = new PdfConvertOptions();

            converter.convert("conversion-word-to-pdf.pdf", convertOptions);
        }
    }
}
