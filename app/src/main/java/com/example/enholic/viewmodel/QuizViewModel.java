package com.example.enholic.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.enholic.Model.QuizModel;
import com.example.enholic.repository.QuizRepository;

public class QuizViewModel extends ViewModel implements QuizRepository.OnQuizLoad{
    private MutableLiveData<QuizModel>  quizModelMutableLiveData;
    private QuizRepository repository;

    public QuizViewModel(){
        quizModelMutableLiveData = new MutableLiveData<>();
        repository = new QuizRepository(this);
    }

    public void setQuizId(String quizId){
        repository.setQuizID(quizId);
    }
    @Override
    public void onLoad(QuizModel quizModel) {
        quizModelMutableLiveData.setValue(quizModel);
    }

    @Override
    public void onError(Exception e) {
        Log.d("QuizError", "onError: "+ e.getMessage());
    }
}
