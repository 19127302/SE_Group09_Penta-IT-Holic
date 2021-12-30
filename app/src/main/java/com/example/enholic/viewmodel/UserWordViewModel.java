package com.example.enholic.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.enholic.Adapter.UserWordListAdapter;
import com.example.enholic.Model.UserWordModel;
import com.example.enholic.Model.WordModel;
import com.example.enholic.repository.AuthRepository;
import com.example.enholic.repository.UserWordRepository;
import com.example.enholic.repository.WordRepository;

import java.util.List;

public class UserWordViewModel extends AndroidViewModel implements UserWordRepository.OnUserWordLoad {
    private MutableLiveData<UserWordModel> userWordLiveData = new MutableLiveData<>();
    private AuthRepository authRepository;

    private UserWordRepository repository;

    public UserWordViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        repository =  new UserWordRepository(this);

        repository.setUser_id(authRepository.getCurrentUser().getUid());
        repository.getWord();
    }

    public MutableLiveData<UserWordModel> getUserWordLiveData() {
        return userWordLiveData;
    }

    public UserWordRepository getRepository() {
        return repository;
    }

    @Override
    public void onLoad(UserWordModel userwordModel) {
        userWordLiveData.setValue(userwordModel);
    }

    @Override
    public void onError(Exception e) {
        Log.d("Bookmarked_Word_ERROR", "onError" + e.getMessage());
    }
}
