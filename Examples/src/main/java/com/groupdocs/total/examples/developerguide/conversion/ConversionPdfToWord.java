package com.groupdocs.total.examples.developerguide.conversion;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.WordProcessingConvertOptions;
import com.groupdocs.total.examples.ExampleFiles;

public class ConversionPdfToWord {
    public static void run() throws Exception {
        try (Converter converter = new Converter(ExampleFiles.path("contract.pdf"))) {
            WordProcessingConvertOptions convertOptions = new WordProcessingConvertOptions();

            converter.convert(ExampleFiles.output("conversion-pdf-to-word.docx"), convertOptions);
        }
    }
}
