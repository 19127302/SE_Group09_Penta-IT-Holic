package com.example.enholic.Model;

import androidx.legacy.content.WakefulBroadcastReceiver;

import java.util.ArrayList;
import com.google.firebase.firestore.DocumentId;

public class WordModel {
    @DocumentId
    private ArrayList<MeaningModel> meaning;
    public WordModel() {}

    public ArrayList<MeaningModel> getMeaning() {
        return meaning;
    }

    public void setMeaning(ArrayList<MeaningModel> meaning) {
        this.meaning = meaning;
    }

    public WordModel(ArrayList<MeaningModel> meaning) {
        this.meaning = meaning;
    }
}
