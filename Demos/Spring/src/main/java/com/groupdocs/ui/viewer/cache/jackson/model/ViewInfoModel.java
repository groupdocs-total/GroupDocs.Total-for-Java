package com.groupdocs.ui.viewer.cache.jackson.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groupdocs.viewer.FileType;
import com.groupdocs.viewer.results.Page;
import com.groupdocs.viewer.results.ViewInfo;

import java.util.List;

public class ViewInfoModel implements ViewInfo {
    @JsonProperty("FileType")
    private String mFileType;
    @JsonProperty("Pages")
    private List<Page> mPages;

    @JsonCreator
    public ViewInfoModel(@JsonProperty("FileType") String fileType, @JsonProperty("Pages") List<Page> pages) {
        mFileType = fileType;
        mPages = pages;
    }

    @Override
    public FileType getFileType() {
        return FileType.valueOf(mFileType);
    }

    @Override
    public List<Page> getPages() {
        return mPages;
    }
}
