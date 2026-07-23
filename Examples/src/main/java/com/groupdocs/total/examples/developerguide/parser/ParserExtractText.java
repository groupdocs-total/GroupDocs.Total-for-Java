package com.groupdocs.total.examples.developerguide.parser;

import com.groupdocs.parser.Parser;
import com.groupdocs.parser.data.TextReader;

public class ParserExtractText {
    public static void run() throws Exception {
        try (Parser parser = new Parser("contract.docx")) {
            try (TextReader reader = parser.getText()) {
                if (reader == null) {
                    System.out.println("Text extraction is not supported for this format.");
                } else {
                    System.out.println(reader.readToEnd());
                }
            }
        }
    }
}
