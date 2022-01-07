package com.example.enholic.viewmodel;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.enholic.repository.AuthRepository;
import com.example.enholic.repository.ReceivedRewardListRepository;
import com.example.enholic.repository.UserBookmarkWordRepository;

public class ReceivedRewardListViewModel extends AndroidViewModel {

    private ReceivedRewardListRepository repository;
    private AuthRepository authRepository;

    public ReceivedRewardListViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        repository = new ReceivedRewardListRepository();

        setUserId(authRepository.getCurrentUser().getUid());
    }

    public void setRewardId(String rewardId) {
        repository.setRewardId(rewardId);
    }

    public void setUserId(String userId) {
        repository.setUserId(userId);
    }

    public void saveReceivedReward() {
        repository.saveReceivedReward();
    }
}
