package com.groupdocs.ui.viewer.cache.jackson.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groupdocs.viewer.results.Layer;

import java.util.Objects;

public class LayerModel implements Layer {
    @JsonProperty("Name")
    private final String mName;
    @JsonProperty("Visible")
    private boolean mVisible;

    @JsonCreator
    public LayerModel(@JsonProperty("Name") String name, @JsonProperty("Visible") boolean visible) {
        mName = name;
        mVisible = visible;
    }

    public LayerModel(String name) {
        mName = name;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public boolean isVisible() {
        return mVisible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LayerModel that = (LayerModel) o;
        return mVisible == that.mVisible &&
                Objects.equals(mName, that.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mName, mVisible);
    }
}
