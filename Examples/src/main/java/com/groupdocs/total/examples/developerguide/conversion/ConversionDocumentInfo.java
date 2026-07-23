package com.groupdocs.total.examples.developerguide.conversion;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.contracts.documentinfo.IDocumentInfo;

public class ConversionDocumentInfo {
    public static void run() throws Exception {
        try (Converter converter = new Converter("contract.pdf")) {
            IDocumentInfo info = converter.getDocumentInfo();

            System.out.println("Format: " + info.getFormat());
            System.out.println("Pages: " + info.getPagesCount());
            System.out.println("Size: " + info.getSize() + " bytes");
        }
    }
}
