package com.groupdocs.total.examples.developerguide.parser;

import com.groupdocs.parser.Parser;
import com.groupdocs.parser.data.TextReader;
import com.groupdocs.total.examples.ExampleFiles;

public class ParserExtractPage {
    public static void run() throws Exception {
        try (Parser parser = new Parser(ExampleFiles.path("contract.docx"))) {
            // First page, index 0
            try (TextReader reader = parser.getText(0)) {
                if (reader == null) {
                    System.out.println("Text extraction is not supported for this format.");
                } else {
                    System.out.println(reader.readToEnd());
                }
            }
        }
    }
}
