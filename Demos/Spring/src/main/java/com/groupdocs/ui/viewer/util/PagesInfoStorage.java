package com.groupdocs.ui.viewer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupdocs.ui.viewer.exception.DiskAccessException;
import com.groupdocs.ui.viewer.exception.ReadWriteException;
import com.groupdocs.viewer.results.Page;
import com.groupdocs.viewer.results.ViewInfo;
import com.groupdocs.viewer.utils.PathUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PagesInfoStorage {
    private static final String FILE_NAME = "PagesInfo.xml";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static int loadPageAngle(String fileCacheSubFolder, int pageNumber) {
        try {
            final File pagesInfoFile = new File(fileCacheSubFolder.endsWith(FILE_NAME) ? fileCacheSubFolder : PathUtils.combine(fileCacheSubFolder, FILE_NAME));
            return MAPPER.readValue(FileUtils.readFileToByteArray(pagesInfoFile), PagesInfo.class).getPageByNumber(pageNumber).getAngle();
        } catch (Exception e) {
            throw new ReadWriteException(e);
        }
    }

    public static void savePageAngle(String fileCacheSubFolder, int pageNumber, int newAngle) {
        try {
            Path pagesInfoFile = Paths.get(fileCacheSubFolder.endsWith(FILE_NAME) ? fileCacheSubFolder : PathUtils.combine(fileCacheSubFolder, FILE_NAME));

            if (Files.notExists(pagesInfoFile)) {
                Files.createDirectories(pagesInfoFile);
            }

            if (Files.exists(pagesInfoFile)) {
                PagesInfoStorage.PagesInfo pagesInfo = MAPPER.readValue(pagesInfoFile.toFile(), PagesInfoStorage.PagesInfo.class);
                final PagesInfoStorage.PagesInfo.PageData pageData = pagesInfo.getPageByNumber(pageNumber);
                pageData.setAngle(newAngle);

                MAPPER.writeValue(pagesInfoFile.toFile(), pagesInfo);
            }
        } catch (Exception e) {
            throw new ReadWriteException(e);
        }
    }

    public static String createPagesInfo(String fileCacheSubFolder, ViewInfo viewInfo, boolean isViewerLicenseSet) {
        try {
            final String subDir = ViewerUtils.replaceChars(fileCacheSubFolder);
            Path fileInfoDir = Paths.get(subDir);
            if (Files.notExists(fileInfoDir)) {
                Files.createDirectories(fileInfoDir);
            }

            Path pagesInfoPath = fileInfoDir.resolve(FILE_NAME);
            if (Files.notExists(pagesInfoPath)) {
                final PagesInfoStorage.PagesInfo pagesInfo = new PagesInfoStorage.PagesInfo();
                List<Page> pages = viewInfo.getPages();
                for (int i = 0, pagesSize = pages.size(); i < pagesSize; i++) {
                    Page page = pages.get(i);
                    if (!isViewerLicenseSet && i == 2) {
                        break; // only 2 pages in evaluation mode
                    }
                    final PagesInfoStorage.PagesInfo.PageData pageData = new PagesInfoStorage.PagesInfo.PageData();
                    pageData.setNumber(page.getNumber());
                    pageData.setAngle(0);
                    pagesInfo.getPages().add(pageData);
                }
                MAPPER.writeValue(pagesInfoPath.toFile(), pagesInfo);
            }
            return pagesInfoPath.toAbsolutePath().toString();
        } catch (Exception e) {
            throw new DiskAccessException("create pages info directory", fileCacheSubFolder);
        }
    }

    public static class PagesInfo {

        private List<PageData> pages = new ArrayList<>();

        public List<PageData> getPages() {
            return pages;
        }

        public void setPages(List<PageData> pages) {
            this.pages = pages;
        }

        public PageData getPageByNumber(int pageNumber) {
            for (PageData pageData : getPages()) {
                if (pageData.getNumber() == pageNumber) {
                    return pageData;
                }
            }
            return null;
        }

        public static class PageData {
            private int number;
            private int angle = 0;

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public int getAngle() {
                return angle;
            }

            public void setAngle(int angle) {
                this.angle = angle;
            }
        }
    }
}
