package com.example.enholic.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.enholic.Model.QuizModel;
import com.example.enholic.repository.QuizRepository;

public class QuizViewModel extends ViewModel implements QuizRepository.OnQuizLoad{
    private MutableLiveData<QuizModel>  quizMutableLiveData;
    private QuizRepository repository;

    public MutableLiveData<QuizModel> getQuizMutableLiveData() {
        return quizMutableLiveData;
    }

    public QuizViewModel(){
        quizMutableLiveData = new MutableLiveData<>();
        repository = new QuizRepository(this);
    }

    public void setQuizId(String quizId){
        repository.setQuizID(quizId);
        repository.getQuiz();
    }
    @Override
    public void onLoad(QuizModel quizModel) {
        quizMutableLiveData.setValue(quizModel);
    }

    @Override
    public void onError(Exception e) {
        Log.d("QuizError", "onError: "+ e.getMessage());
    }
}
