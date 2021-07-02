package com.groupdocs.ui.viewer.cache.jackson.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groupdocs.viewer.results.Line;
import com.groupdocs.viewer.results.Word;

import java.util.List;

public class LineModel extends TextElementModel<String> implements Line {
    @JsonProperty("Words")
    private final List<Word> mWords;

    @JsonCreator
    public LineModel(@JsonProperty("Line") String line, @JsonProperty("X") double x, @JsonProperty("Y") double y, @JsonProperty("Width") double width, @JsonProperty("Height") double height, @JsonProperty("Words") List<Word> words) {
        super(line, x, y, width, height);
        this.mWords = words;
    }

    @Override
    public List<Word> getWords() {
        return mWords;
    }
}
