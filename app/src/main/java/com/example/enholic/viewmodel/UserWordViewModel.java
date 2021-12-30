package com.example.enholic.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.enholic.Adapter.UserWordListAdapter;
import com.example.enholic.Model.UserWordModel;
import com.example.enholic.Model.WordModel;
import com.example.enholic.repository.UserWordRepository;
import com.example.enholic.repository.WordRepository;

import java.util.List;

public class UserWordViewModel extends ViewModel implements UserWordRepository.OnUserWordLoad {
    private MutableLiveData<List<UserWordModel>> userWordLiveData = new MutableLiveData<>();

    private UserWordRepository repository = new UserWordRepository(this);

    public UserWordViewModel() {
        repository.getWord();
    }

    public MutableLiveData<List<UserWordModel>> getUserWordLiveData() {
        return userWordLiveData;
    }

    public UserWordRepository getRepository() {
        return repository;
    }

    @Override
    public void onLoad(UserWordModel userwordModel) {
        userWordLiveData.setValue((List<UserWordModel>) userwordModel);
    }

    @Override
    public void onError(Exception e) {
        Log.d("Bookmarked_Word_ERROR", "onError" + e.getMessage());
    }
}
