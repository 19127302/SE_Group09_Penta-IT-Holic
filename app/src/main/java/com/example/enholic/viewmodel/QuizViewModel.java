package com.example.enholic.viewmodel;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.enholic.Model.QuizModel;
import com.example.enholic.repository.AuthRepository;
import com.example.enholic.repository.QuizRepository;


public class QuizViewModel extends AndroidViewModel implements QuizRepository.OnQuizLoad{
    private MutableLiveData<QuizModel>  quizMutableLiveData;
    private QuizRepository repository;
    private AuthRepository authRepository;

    public MutableLiveData<QuizModel> getQuizMutableLiveData() {
        return quizMutableLiveData;
    }

    public QuizViewModel(@NonNull Application application){
        super(application);
        quizMutableLiveData = new MutableLiveData<>();
        repository = new QuizRepository(this);
        authRepository = new AuthRepository(application);
    }

    public void setQuizId(String quizId){
        repository.setQuizID(quizId);
        repository.getQuiz();
    }

    public Long GetUserCurrentEx(){
        return authRepository.getUserModel().getCurrentEx();
    }

    public String GetUserCurrentLevel(){
        return authRepository.getUserModel().getLevel();
    }

    public void UpdateEx(Long ExNumber){
        if (ExNumber > 3)
        {
            String NewLevel = "";
            authRepository.updateUserProfileLevel(authRepository.getCurrentUser().getUid(),"intermediate");
        }
        authRepository.updateUserProfileCurrentEx(authRepository.getCurrentUser().getUid(),ExNumber);
    }

    public void UpdatePoint(){
        authRepository.updateUserProfileEnPoint(authRepository.getCurrentUser().getUid(),authRepository.getUserModel().getEnPoint()+50);
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
