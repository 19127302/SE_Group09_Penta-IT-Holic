package com.example.enholic.Model;

import com.google.firebase.firestore.DocumentId;

public class UserModel {
    @DocumentId
    private String level;
    private Long currentEx;
    private Long enPoint;

    public UserModel() {}

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getCurrentEx() {
        return currentEx;
    }

    public void setCurrentEx(Long currentEx) {
        this.currentEx = currentEx;
    }

    public Long getEnPoint() {
        return enPoint;
    }

    public void setEnPoint(Long enPoint) {
        this.enPoint = enPoint;
    }
}