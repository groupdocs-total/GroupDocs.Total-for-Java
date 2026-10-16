package com.groupdocs.total.examples.quickstart;

import com.groupdocs.total.License;

import java.io.File;

// Set the Total license from a file.
public class SetLicenseFromFile {

    public static void run() {
        String licensePath = licensePath();
        System.out.println("try to set LicenseFromFile, path: " + licensePath);
        try {
            if (licensePath != null && new File(licensePath).exists()) {
                License.setLicense(licensePath);
                System.out.println("License set successfully for all GroupDocs.Total products.");
            } else {
                System.out.println("License file does not exist at the specified path: " + licensePath);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    static String licensePath() {
        String licensePath = System.getenv("GROUPDOCS_LIC_PATH");
        if (licensePath != null && !licensePath.isEmpty()) {
            return licensePath;
        }
        File[] here = new File(".").listFiles();
        if (here != null) {
            for (File file : here) {
                if (file.getName().endsWith(".lic")) {
                    return file.getPath();
                }
            }
        }
        return null;
    }
}
