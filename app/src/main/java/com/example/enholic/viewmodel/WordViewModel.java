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
    private WordModel wordModel;

    public WordViewModel() {
        wordMutableLiveData = new MutableLiveData<>();
        repository = new WordRepository(this);
    }

    public WordModel getWordModel() {
        Log.d("WordViewModel", "Get word model");
        return wordModel;
    }

    public void setWordId(String wordId) {
        Log.d("WordViewModel", "Set word id and call repo.getWordModel()");

        repository.setWordId(wordId);
        repository.getWord();

        //wordModel = repository.getWordModel();
        //Log.d("WordViewModel", wordModel.toString());
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
