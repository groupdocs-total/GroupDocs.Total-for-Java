package com.groupdocs.total.examples.developerguide.conversion;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.WordProcessingConvertOptions;

public class ConversionPdfToWord {
    public static void run() throws Exception {
        try (Converter converter = new Converter("contract.pdf")) {
            WordProcessingConvertOptions convertOptions = new WordProcessingConvertOptions();

            converter.convert("conversion-pdf-to-word.docx", convertOptions);
        }
    }
}
