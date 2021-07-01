package com.groupdocs.ui.viewer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupdocs.ui.viewer.exception.ReadWriteException;
import com.groupdocs.viewer.results.Page;
import com.groupdocs.viewer.results.ViewInfo;
import org.apache.commons.io.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PagesInfoStorage {
    private static final String FILE_NAME = "PagesInfo.xml";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static int loadPageAngle(Path resourceDir, String documentGuid, int pageNumber) {
        try {
            final String subDir = ViewerUtils.replaceChars(Paths.get(documentGuid).getFileName().toString());
            final Path pagesInfoFile = resourceDir.resolve(subDir).resolve(FILE_NAME);
            if (Files.notExists(pagesInfoFile)) {
                return 0;
            }
            return MAPPER.readValue(FileUtils.readFileToByteArray(pagesInfoFile.toFile()), PagesInfo.class).getPageByNumber(pageNumber).getAngle();
        } catch (Exception e) {
            throw new ReadWriteException(e);
        }
    }

    public static void savePageAngle(Path resourceDir, String documentGuid, int pageNumber, int newAngle) {
        try {
            final String subDir = ViewerUtils.replaceChars(Paths.get(documentGuid).getFileName().toString());
            Path fileInfoDir = resourceDir.resolve(subDir);
            if (Files.notExists(fileInfoDir)) {
                Files.createDirectories(fileInfoDir);
            }
            Path pagesInfoFile = fileInfoDir.resolve(FILE_NAME);

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

    public static void createPagesInfo(Path resourceDir, String documentGuid, ViewInfo viewInfo) {
        try {
            final String subDir = ViewerUtils.replaceChars(Paths.get(documentGuid).getFileName().toString());
            Path fileInfoDir = resourceDir.resolve(subDir);
            if (Files.notExists(fileInfoDir)) {
                Files.createDirectories(fileInfoDir);
            }

            Path pagesInfoPath = fileInfoDir.resolve(FILE_NAME);
            if (Files.notExists(pagesInfoPath)) {
                final PagesInfoStorage.PagesInfo pagesInfo = new PagesInfoStorage.PagesInfo();
                for (Page page : viewInfo.getPages()) {
                    final PagesInfoStorage.PagesInfo.PageData pageData = new PagesInfoStorage.PagesInfo.PageData();
                    pageData.setNumber(page.getNumber());
                    pageData.setAngle(0);
                    pagesInfo.getPages().add(pageData);
                }
                MAPPER.writeValue(pagesInfoPath.toFile(), pagesInfo);
            }
        } catch (Exception e) {
            throw new ReadWriteException(e);
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
