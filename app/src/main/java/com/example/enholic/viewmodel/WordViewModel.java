package com.example.enholic.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.enholic.Model.WordModel;
import com.example.enholic.repository.WordRepository;

import java.util.List;

public class WordViewModel extends ViewModel implements WordRepository.OnWordLoad{

    private MutableLiveData<WordModel> wordMutableLiveData;
    private WordRepository repository;

    public WordViewModel() {
        wordMutableLiveData = new MutableLiveData<>();
        repository = new WordRepository(this);
    }

    public void setWordId(String wordId) {
        repository.setWordId(wordId);
        repository.getWords();
    }

    public MutableLiveData<WordModel> getWordMutableLiveData() {
        return wordMutableLiveData;
    }

    @Override
    public void onLoad(WordModel wordModel) {
        wordMutableLiveData.setValue(wordModel);
    }

    @Override
    public void onError(Exception e) {
        Log.d("WordError", "onError: " + e.getMessage());
    }
}
