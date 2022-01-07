package com.example.enholic.Model;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;

public class UserRewardModel {
    @DocumentId
    // private AuthRepository authRepository;
    private ArrayList<String> reward;

    public UserRewardModel() { }
    public UserRewardModel(ArrayList<String> reward) {
        this.reward = reward;
    }

    public ArrayList<String> getRewards() {
        return reward;
    }

    public void setWords(ArrayList<String> reward) {
        this.reward = reward;
    }

}
