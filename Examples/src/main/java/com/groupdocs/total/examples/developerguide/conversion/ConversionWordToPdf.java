package com.groupdocs.total.examples.developerguide.conversion;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.PdfConvertOptions;
import com.groupdocs.total.examples.ExampleFiles;

public class ConversionWordToPdf {
    public static void run() throws Exception {
        try (Converter converter = new Converter(ExampleFiles.path("contract.docx"))) {
            PdfConvertOptions convertOptions = new PdfConvertOptions();

            converter.convert(ExampleFiles.output("conversion-word-to-pdf.pdf"), convertOptions);
        }
    }
}
