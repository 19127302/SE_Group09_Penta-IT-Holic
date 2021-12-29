package com.example.enholic.Model;

import com.google.firebase.firestore.DocumentId;

public class MeaningModel {

    @DocumentId
    private String word_class;
    private String definition;
    private String example;

    public MeaningModel() {}

    public String getWord_class() {
        return word_class;
    }

    public void setWord_class(String word_class) {
        this.word_class = word_class;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public MeaningModel(String word_class, String definition, String example) {
        this.word_class = word_class;
        this.definition = definition;
        this.example = example;
    }
}
