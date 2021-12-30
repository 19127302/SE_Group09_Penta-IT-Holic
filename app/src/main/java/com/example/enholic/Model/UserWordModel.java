package com.example.enholic.Model;

import com.example.enholic.repository.AuthRepository;
import com.google.firebase.firestore.DocumentId;

public class UserWordModel {

    @DocumentId
    // private AuthRepository authRepository;
    private String user_id;
    private WordModel word;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public WordModel getWord() {
        return word;
    }

    public void setWord(WordModel word) {
        this.word = word;
    }

    public UserWordModel() { }
    public UserWordModel(String user_id, WordModel word) {
        this.user_id = user_id;
        this.word = word;
    }
}
