package com.groupdocs.total.examples.developerguide.signature;

import com.groupdocs.signature.Signature;
import com.groupdocs.signature.domain.qrcodes.QrCodeTypes;
import com.groupdocs.signature.domain.signatures.QrCodeSignature;
import com.groupdocs.signature.options.search.QrCodeSearchOptions;
import com.groupdocs.signature.options.sign.QrCodeSignOptions;
import com.groupdocs.total.examples.ExampleFiles;
import java.util.List;

public class SignatureSearch {
    public static void run() throws Exception {
        // Sign, then search the result
        Signature signature = new Signature(ExampleFiles.path("contract.docx"));
        QrCodeSignOptions signOptions = new QrCodeSignOptions("Approved by John Smith");
        signOptions.setEncodeType(QrCodeTypes.QR);
        signOptions.setLeft(100);
        signOptions.setTop(100);
        signature.sign(ExampleFiles.output("signature-search.docx"), signOptions);

        Signature searcher = new Signature(ExampleFiles.output("signature-search.docx"));
        QrCodeSearchOptions searchOptions = new QrCodeSearchOptions();
        List<QrCodeSignature> signatures = searcher.search(QrCodeSignature.class, searchOptions);

        System.out.println("Signatures found: " + signatures.size());

        for (QrCodeSignature qrSignature : signatures) {
            System.out.println("Text: " + qrSignature.getText());
        }
    }
}
