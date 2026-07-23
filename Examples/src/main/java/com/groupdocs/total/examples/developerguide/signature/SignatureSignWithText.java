package com.groupdocs.total.examples.developerguide.signature;

import com.groupdocs.signature.Signature;
import com.groupdocs.signature.options.sign.TextSignOptions;

public class SignatureSignWithText {
    public static void run() throws Exception {
        Signature signature = new Signature("contract.docx");

        TextSignOptions signOptions = new TextSignOptions("John Smith");
        signOptions.setLeft(100);
        signOptions.setTop(100);
        signOptions.setWidth(200);
        signOptions.setHeight(40);

        signature.sign("signature-sign-with-text.docx", signOptions);
    }
}
