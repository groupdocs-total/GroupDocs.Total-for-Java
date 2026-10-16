package com.groupdocs.total.examples.quickstart;

import com.groupdocs.total.License;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

// Set the Total license from a stream.
public class SetLicenseFromStream {

    public static void run() {
        String licensePath = SetLicenseFromFile.licensePath();
        try {
            if (licensePath != null && new File(licensePath).exists()) {
                try (InputStream stream = new FileInputStream(licensePath)) {
                    License.setLicense(stream);
                }
                System.out.println("License set successfully from stream for all GroupDocs.Total products.");
            } else {
                System.out.println("License file does not exist at the specified path: " + licensePath);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}
