package com.example.enholic.repository;

import android.util.Log;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReceivedRewardListRepository {
    private FirebaseFirestore firebaseFirestore;
    private String userId;
    private String rewardId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public ReceivedRewardListRepository()  {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public ReceivedRewardListRepository(String userId, String rewardId) {
        this.userId = userId;
        this.rewardId = rewardId;

        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void saveReceivedReward() {
        Log.d("RewardListRepo", "UserID: " + userId);
        Log.d("RewardListRepo", "RewardID: " + rewardId);

        firebaseFirestore.collection("User_Reward").document(userId)
                .update("reward", FieldValue.arrayUnion(rewardId));
    }
}
