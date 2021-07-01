package com.groupdocs.ui.viewer.util;

import com.groupdocs.ui.viewer.config.ViewerConfiguration;
import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.results.ViewInfo;
import org.springframework.http.MediaType;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewerUtils {

    public static MediaType detectMediaType(String fileName) {
        if (fileName.contains(".")) {
            final String extension = fileName.substring(fileName.lastIndexOf("."));
            return MediaType.parseMediaType(MediaTypes.detectMediaTypeForWeb(extension));
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    public static Path makeResourcesDir(ViewerConfiguration viewerConfiguration) {
        return Paths.get(viewerConfiguration.getFilesDirectory(), viewerConfiguration.getResourcesFolderName());
    }

    public static String replaceChars(String documentGuid) {
        return documentGuid.replace('.', '_');
    }
}
