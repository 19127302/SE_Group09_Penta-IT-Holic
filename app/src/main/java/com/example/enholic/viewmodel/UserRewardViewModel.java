package com.example.enholic.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.enholic.Model.UserRewardModel;
import com.example.enholic.repository.AuthRepository;
import com.example.enholic.repository.UserRewardRepository;

public class UserRewardViewModel extends AndroidViewModel implements UserRewardRepository.OnUserRewardLoad {
    private MutableLiveData<UserRewardModel> userRewardLiveData = new MutableLiveData<>();
    private AuthRepository authRepository;

    private UserRewardRepository repository;

    public UserRewardViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        repository =  new UserRewardRepository (this);

        repository.setUser_id(authRepository.getCurrentUser().getUid());
        repository.getReward();
    }

    public MutableLiveData<UserRewardModel> getUserRewardLiveData() {
        return userRewardLiveData;
    }

    public UserRewardRepository getRepository() {
        return repository;
    }

    @Override
    public void onLoad(UserRewardModel userrewardModel) {
        userRewardLiveData.setValue(userrewardModel);
    }

    @Override
    public void onError(Exception e) {
        Log.d("Received_Reward_ERROR", "onError" + e.getMessage());
    }
}
