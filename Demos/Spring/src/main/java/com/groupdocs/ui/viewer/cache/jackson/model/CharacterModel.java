package com.groupdocs.ui.viewer.cache.jackson.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groupdocs.viewer.results.Character;

public class CharacterModel extends TextElementModel<java.lang.Character> implements Character {
    @JsonCreator
    public CharacterModel(@JsonProperty("Character") java.lang.Character character, @JsonProperty("X") double x, @JsonProperty("Y") double y, @JsonProperty("Width") double width, @JsonProperty("Height") double height) {
        super(character, x, y, width, height);
    }
}
