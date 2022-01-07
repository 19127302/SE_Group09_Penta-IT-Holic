package com.example.enholic.Model;

import com.google.firebase.firestore.DocumentId;

public class UserModel {
    @DocumentId
    private String level;
    private Long currentEx, bGb, sGb;
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

    public Long getbGb() {
        return bGb;
    }

    public void setbGb(Long bGb) {
        this.bGb = bGb;
    }
    public Long getsGb() {
        return sGb;
    }

    public void setsGb(Long sGb) {
        this.sGb = sGb;
    }
    public Long getEnPoint() {
        return enPoint;
    }

    public void setEnPoint(Long enPoint) {
        this.enPoint = enPoint;
    }
}