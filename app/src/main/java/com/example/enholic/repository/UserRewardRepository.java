package com.example.enholic.repository;

import android.util.Log;

import com.example.enholic.Model.UserRewardModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserRewardRepository {
    private FirebaseFirestore firebaseFirestore;
    private String user_id;
    private UserRewardRepository.OnUserRewardLoad onUserRewardLoad;
    private UserRewardModel userrewardModel;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public UserRewardRepository(UserRewardRepository.OnUserRewardLoad onUserRewardLoad) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onUserRewardLoad = onUserRewardLoad;
    }

    public UserRewardModel getUserRewardModel() {
        return userrewardModel;
    }

    public void getReward() {
        Log.d("UserRewardRepo", "User ID: " + user_id);
        firebaseFirestore.collection("User_Reward").document(user_id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Log.d("UserRewardRepository", documentSnapshot.toString());
                    ArrayList<String> reward_list = (ArrayList<String>) documentSnapshot.get("reward");

                    UserRewardModel user_reward_Model_temp = new UserRewardModel(reward_list);
                    onUserRewardLoad.onLoad(user_reward_Model_temp);
                }
                else {
                    onUserRewardLoad.onError(new Exception("No data"));
                }
            }
        });
    }

    public interface OnUserRewardLoad {
        void onLoad(UserRewardModel userrewardModel);
        void onError(Exception e);
    }
}
