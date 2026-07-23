package com.groupdocs.total.examples.developerguide.signature;

import com.groupdocs.signature.Signature;
import com.groupdocs.signature.domain.qrcodes.QrCodeTypes;
import com.groupdocs.signature.options.sign.QrCodeSignOptions;

public class SignatureSignWithQrCode {
    public static void run() throws Exception {
        Signature signature = new Signature("contract.docx");

        QrCodeSignOptions signOptions = new QrCodeSignOptions("Approved by John Smith");
        signOptions.setEncodeType(QrCodeTypes.QR);
        signOptions.setLeft(100);
        signOptions.setTop(100);
        signOptions.setWidth(120);
        signOptions.setHeight(120);

        signature.sign("signature-sign-with-qr-code.docx", signOptions);
    }
}
