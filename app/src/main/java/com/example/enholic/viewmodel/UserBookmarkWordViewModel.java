package com.example.enholic.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.enholic.repository.AuthRepository;
import com.example.enholic.repository.UserBookmarkWordRepository;

public class UserBookmarkWordViewModel extends AndroidViewModel {

    private UserBookmarkWordRepository repository;
    private AuthRepository authRepository;

    public UserBookmarkWordViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        repository = new UserBookmarkWordRepository();

        setUserId(authRepository.getCurrentUser().getUid());
    }

    public void setWordId(String wordId) {
        repository.setWordId(wordId);
    }

    public void setUserId(String userId) {
        repository.setUserId(userId);
    }

    public void saveBookmark() {
        repository.saveBookmarkWord();
    }
}
