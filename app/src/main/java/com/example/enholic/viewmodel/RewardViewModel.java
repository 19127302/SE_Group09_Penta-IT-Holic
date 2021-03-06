package com.example.enholic.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import androidx.lifecycle.AndroidViewModel;

import com.example.enholic.Model.RewardModel;
import com.example.enholic.Model.UserModel;
import com.example.enholic.repository.AuthRepository;
import com.example.enholic.repository.QuizRepository;
import com.example.enholic.repository.RewardRepository;
import com.google.firebase.auth.FirebaseUser;

public class RewardViewModel extends AndroidViewModel implements RewardRepository.OnRewardLoad, AuthRepository.OnUserLoad{
    private MutableLiveData<RewardModel> rewardMutableLiveData;
    private MutableLiveData<UserModel> userModelMutableLiveData;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private RewardRepository repository;
    private AuthRepository authRepository;
    private FirebaseUser currentUser;


    public MutableLiveData<RewardModel> getRewardMutableLiveData() {
        return rewardMutableLiveData;
    }
    public MutableLiveData<UserModel> getUserModelMutableLiveData() {
        return userModelMutableLiveData;
    }
    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }
    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public RewardViewModel(@NonNull Application application) {
        super(application);
        userModelMutableLiveData = new MutableLiveData<>();
        rewardMutableLiveData = new MutableLiveData<>();
        repository = new RewardRepository(this);
        authRepository = new AuthRepository(application, this);
        currentUser = authRepository.getCurrentUser();
        firebaseUserMutableLiveData = authRepository.getFirebaseUserMutableLiveData();
    }

    public void setRewardId(String rewardId) {
        repository.setRewardID(rewardId);
        repository.getReward();
    }


    public void loadUserProfile() {
        authRepository.loadUserProfile(authRepository.getCurrentUser().getUid());
    }

    public String getCurrentUserName(){
        return authRepository.getCurrentUser().getDisplayName();
    }

    public void UpdatePoint(Long enPoint) {
        authRepository.updateUserProfileEnPoint(authRepository.getCurrentUser().getUid(), enPoint);
    }

    public void UpdatebGb(Long bGb) {
        authRepository.updateUserbGb(authRepository.getCurrentUser().getUid(), bGb);
    }

    public void UpdatesGb(Long sGb) {
        authRepository.updateUsersGb(authRepository.getCurrentUser().getUid(), sGb);
    }

    @Override
    public void onLoadReward(RewardModel rewardModel) {
        rewardMutableLiveData.setValue(rewardModel);
    }
    @Override
    public void onError(Exception e) {
        Log.d("RewardError", "onError: " + e.getMessage());
    }

    @Override
    public void onLoad(UserModel userModel) {
        userModelMutableLiveData.setValue(userModel);
    }
}
