package com.groupdocs.total.examples.developerguide.signature;

import com.groupdocs.signature.Signature;
import com.groupdocs.signature.domain.qrcodes.QrCodeTypes;
import com.groupdocs.signature.domain.signatures.QrCodeSignature;
import com.groupdocs.signature.options.search.QrCodeSearchOptions;
import com.groupdocs.signature.options.sign.QrCodeSignOptions;
import java.util.List;

public class SignatureSearch {
    public static void run() throws Exception {
        // Sign first, so there is something to find
        Signature signature = new Signature("contract.docx");
        QrCodeSignOptions signOptions = new QrCodeSignOptions("Approved by John Smith");
        signOptions.setEncodeType(QrCodeTypes.QR);
        signOptions.setLeft(100);
        signOptions.setTop(100);
        signature.sign("signature-search.docx", signOptions);

        // Now search the signed document
        Signature searcher = new Signature("signature-search.docx");
        QrCodeSearchOptions searchOptions = new QrCodeSearchOptions();
        List<QrCodeSignature> signatures = searcher.search(QrCodeSignature.class, searchOptions);

        System.out.println("Signatures found: " + signatures.size());

        for (QrCodeSignature qrSignature : signatures) {
            System.out.println("Text: " + qrSignature.getText());
        }
    }
}
