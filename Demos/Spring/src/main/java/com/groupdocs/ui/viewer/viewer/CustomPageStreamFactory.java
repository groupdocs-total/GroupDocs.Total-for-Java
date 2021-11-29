package com.groupdocs.ui.viewer.viewer;

import com.groupdocs.ui.viewer.cache.ViewerCache;
import com.groupdocs.viewer.interfaces.PageStreamFactory;
import org.apache.commons.io.FileUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

class CustomPageStreamFactory implements PageStreamFactory {
    private final String mExtension;
    private final Path mDocumentResourcesDir;
    private final ViewerCache mCache;

    public CustomPageStreamFactory(Path documentResourcesDir, String extension, ViewerCache viewerCache) {
        this.mExtension = extension;
        this.mCache = viewerCache;
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
    public OutputStream createPageStream(int pageNumber) {
        String fileName = "p" + pageNumber + mExtension;
        Path resourceFilePath;
        if (mCache == null) {
            resourceFilePath = mDocumentResourcesDir.resolve(fileName);
        } else {
            resourceFilePath = mCache.getCacheFilePath(fileName);
        }

        try {
            return new FileOutputStream(resourceFilePath.toFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void closePageStream(int pageNumber, OutputStream outputStream) {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public byte[] getPageContent(int pageNumber) throws IOException {
        String fileName = "p" + pageNumber + mExtension;

        Path cacheFilePath;
        if (mCache == null) {
            cacheFilePath = mDocumentResourcesDir.resolve(fileName);
        } else {
            cacheFilePath = mCache.getCacheFilePath(fileName);
        }
        return FileUtils.readFileToByteArray(cacheFilePath.toFile());
    }
}
