package com.groupdocs.ui.viewer.viewer;

import com.groupdocs.viewer.interfaces.ResourceStreamFactory;
import com.groupdocs.viewer.results.Resource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class CustomResourceStreamFactory implements ResourceStreamFactory {
    private final Path mResourcesDir;
    private final String mSubFolder;

    public CustomResourceStreamFactory(Path resourcesDir, String subFolder) {
        this.mResourcesDir = resourcesDir;
        this.mSubFolder = subFolder;

        final Path subDir = resourcesDir.resolve(subFolder);
        if (Files.notExists(subDir)) {
            try {
                Files.createDirectories(subDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public OutputStream createResourceStream(int pageNumber, Resource resource) {
        final Path subFolderName = mResourcesDir.resolve(mSubFolder);
        if (Files.notExists(subFolderName)) {
            try {
                Files.createDirectory(subFolderName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String resourceName = "p" + pageNumber + "_" + resource.getFileName();
        try {
            return new FileOutputStream(subFolderName.resolve(resourceName).toFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createResourceUrl(int pageNumber, Resource resource) {
        String urlPrefix = "/viewer/resources/" + mSubFolder;
        return urlPrefix + "/p" + pageNumber + "_" + resource.getFileName();
    }

    @Override
    public void closeResourceStream(int pageNumber, Resource resource, OutputStream outputStream) {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
