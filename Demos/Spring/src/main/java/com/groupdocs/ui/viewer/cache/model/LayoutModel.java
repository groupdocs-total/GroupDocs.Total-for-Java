package com.groupdocs.ui.viewer.cache.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.groupdocs.viewer.results.Layout;

import java.util.Objects;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class LayoutModel implements Layout {
    @JsonProperty("Name")
    private final String mName;
    @JsonProperty("Width")
    private final double mWidth;
    @JsonProperty("Height")
    private final double mHeight;

    @JsonCreator
    public LayoutModel(@JsonProperty("Name") String name, @JsonProperty("Width") double width, @JsonProperty("Height") double height) {
        mName = name;
        mWidth = width;
        mHeight = height;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public double getWidth() {
        return mWidth;
    }

    @Override
    public double getHeight() {
        return mHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LayoutModel that = (LayoutModel) o;
        return Double.compare(that.mWidth, mWidth) == 0 && Double.compare(that.mHeight, mHeight) == 0 && Objects.equals(mName, that.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mName, mWidth, mHeight);
    }
}
