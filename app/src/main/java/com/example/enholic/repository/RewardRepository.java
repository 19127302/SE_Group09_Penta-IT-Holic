package com.example.enholic.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.enholic.Model.RewardModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RewardRepository {
    private FirebaseFirestore firebaseFirestore;
    private String rewardID;
    private OnRewardLoad onRewardLoad;

    public void setRewardID(String rewardID) {
        this.rewardID = rewardID;
    }

    public RewardRepository(OnRewardLoad onRewardLoad)
    {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onRewardLoad = onRewardLoad;
    }

    public void getReward()
    {
        firebaseFirestore.collection("Reward").document(rewardID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    onRewardLoad.onLoadReward(task.getResult().toObject(RewardModel.class));
                }else{
                    onRewardLoad.onError(task.getException());
                }

            }
        });
    }

    public interface OnRewardLoad{
        void onLoadReward(RewardModel rewardModel);
        void onError(Exception e);
    }

}

