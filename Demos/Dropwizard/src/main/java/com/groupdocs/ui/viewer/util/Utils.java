package com.groupdocs.ui.viewer.util;

import com.groupdocs.ui.common.exception.PasswordExceptions;
import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.caching.extra.CacheableFactory;
import com.groupdocs.viewer.options.ViewInfoOptions;
import com.groupdocs.viewer.results.Page;
import com.groupdocs.viewer.results.ViewInfo;

import javax.ws.rs.core.MediaType;
import java.util.List;

public class Utils {

    /**
     * Get correct message for security exceptions
     */
    public static String getExceptionMessage(String password) {
        return password == null || password.isEmpty() ? PasswordExceptions.PASSWORD_REQUIRED : PasswordExceptions.INCORRECT_PASSWORD;
    }

    public static MediaType detectMediaType(String fileName) {
        if (fileName.contains(".")) {
            final String extension = fileName.substring(fileName.lastIndexOf("."));
            return MediaType.valueOf(MediaTypes.detectMediaTypeForWeb(extension));
        }
        return MediaType.APPLICATION_OCTET_STREAM_TYPE;
    }

    public static void applyWidthHeightFix(Viewer viewer, ViewInfo viewInfo) {
        // Fix to detect size, because there is a bug with detecting size in HTML mode
        // The bug is already fixed in .NET and will be fixed in the next version of Java viewer
        final ViewInfo fixViewInfo = viewer.getViewInfo(ViewInfoOptions.forPngView(false));
        final List<Page> pages = viewInfo.getPages();
        final List<Page> fixPages = fixViewInfo.getPages();
        int lastFixWidth = 0, lastFixHeight = 0;
        for (int n = 0; n < Math.min(fixPages.size(), pages.size()); n++) {
            final Page page = pages.get(n);
            final Page fixPage = fixPages.get(n);
            int fixWidth = fixPage.getWidth();
            int fixHeight = fixPage.getHeight();
            if (page.getWidth() == 0 && page.getHeight() == 0) {
                // You can use new PageImpl(...) but in this way you can't use custom models in caching mechanism
                // More details are here - https://docs.groupdocs.com/viewer/java/how-to-use-custom-cache-implementation/
                pages.set(n, CacheableFactory.getInstance().newPage(page.getNumber(), page.isVisible(), (fixWidth == 0) ? lastFixWidth : fixWidth, (fixHeight == 0) ? lastFixHeight : fixHeight, page.getLines()));
            }
            lastFixWidth = pages.get(n).getWidth();
            lastFixHeight = pages.get(n).getHeight();
        }
    }
}
