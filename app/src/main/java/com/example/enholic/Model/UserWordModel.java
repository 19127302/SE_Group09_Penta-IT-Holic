package com.example.enholic.Model;

import com.example.enholic.repository.AuthRepository;
import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserWordModel {
    @DocumentId
    // private AuthRepository authRepository;
    private ArrayList<String> words;

    public UserWordModel() { }
    public UserWordModel(ArrayList<String> words) {
        this.words = words;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

}
