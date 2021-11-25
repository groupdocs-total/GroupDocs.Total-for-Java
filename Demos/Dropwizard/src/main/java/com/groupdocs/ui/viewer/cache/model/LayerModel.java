package com.groupdocs.ui.viewer.cache.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.groupdocs.viewer.results.Layer;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
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
}
