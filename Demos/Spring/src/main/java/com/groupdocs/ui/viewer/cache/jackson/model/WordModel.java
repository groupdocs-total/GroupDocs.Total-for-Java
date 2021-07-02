package com.groupdocs.ui.viewer.cache.jackson.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groupdocs.viewer.results.Character;
import com.groupdocs.viewer.results.Word;

import java.util.List;

public class WordModel extends TextElementModel<String> implements Word {
    @JsonProperty("Characters")
    private final List<Character> mCharacters;

    @JsonCreator
    public WordModel(@JsonProperty("Value") String word, @JsonProperty("X") double x, @JsonProperty("Y") double y, @JsonProperty("Width") double width, @JsonProperty("Height") double height, @JsonProperty("Characters") List<Character> characters) {
        super(word, x, y, width, height);
        mCharacters = characters;
    }

    @Override
    public List<Character> getCharacters() {
        return mCharacters;
    }
}
