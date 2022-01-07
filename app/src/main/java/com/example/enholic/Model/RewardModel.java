package com.example.enholic.Model;

import com.google.firebase.firestore.DocumentId;

public class RewardModel {
    @DocumentId
    private String rewardID;
    private String description, example, idiom;
    private long cost;


    public RewardModel () {}
    public String getRewardID() {
        return rewardID;
    }

    public void setRewardID(String rewardID) {
        this.rewardID = rewardID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getIdiom() {
        return idiom;
    }

    public void setIdiom(String idiom) {
        this.idiom = idiom;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public RewardModel(String rewardID, String description, String example, String idiom, long cost) {
        this.rewardID = rewardID;
        this.description = description;
        this.example = example;
        this.idiom = idiom;
        this.cost = cost;
    }
}
