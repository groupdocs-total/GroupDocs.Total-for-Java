package com.groupdocs.total.examples.developerguide.parser;

import com.groupdocs.parser.Parser;
import com.groupdocs.parser.options.IDocumentInfo;

public class ParserDocumentInfo {
    public static void run() throws Exception {
        try (Parser parser = new Parser("contract.docx")) {
            IDocumentInfo info = parser.getDocumentInfo();

            System.out.println("File type: " + info.getFileType().getFileFormat());
            System.out.println("Pages: " + info.getPageCount());
            System.out.println("Size: " + info.getSize() + " bytes");
        }
    }
}
