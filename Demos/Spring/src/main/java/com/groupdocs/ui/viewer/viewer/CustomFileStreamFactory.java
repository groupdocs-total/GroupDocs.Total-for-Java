package com.groupdocs.ui.viewer.viewer;

import com.groupdocs.viewer.interfaces.FileStreamFactory;
import org.apache.commons.io.FileUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Files;

class CustomFileStreamFactory implements FileStreamFactory {
    private final String mExtension;
    private final Path mDocumentResourcesDir;

    public CustomFileStreamFactory(Path documentResourcesDir, String extension) {
        this.mExtension = extension;
        this.mDocumentResourcesDir = documentResourcesDir;
        if (Files.notExists(this.mDocumentResourcesDir)) {
            try {
                Files.createDirectories(this.mDocumentResourcesDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public OutputStream createFileStream() {
        String fileName = "f" + mExtension;
        Path cacheFilePath = mDocumentResourcesDir.resolve(fileName);

        try {
            return new FileOutputStream(cacheFilePath.toFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void closeFileStream(OutputStream outputStream) {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
