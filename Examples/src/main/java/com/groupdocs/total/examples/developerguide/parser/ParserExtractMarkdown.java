package com.groupdocs.total.examples.developerguide.parser;

import com.groupdocs.parser.Parser;
import com.groupdocs.parser.data.TextReader;
import com.groupdocs.parser.options.FormattedTextMode;
import com.groupdocs.parser.options.FormattedTextOptions;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParserExtractMarkdown {
    public static void run() throws Exception {
        try (Parser parser = new Parser("contract.docx")) {
            try (TextReader reader = parser.getFormattedText(
                    new FormattedTextOptions(FormattedTextMode.Markdown))) {
                if (reader == null) {
                    System.out.println("Formatted extraction is not supported for this format.");
                } else {
                    Files.write(Paths.get("parser-extract-markdown.md"),
                        reader.readToEnd().getBytes(StandardCharsets.UTF_8));
                }
            }
        }
    }
}
