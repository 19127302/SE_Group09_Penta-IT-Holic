package com.example.enholic.viewmodel;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.enholic.Model.QuizModel;
import com.example.enholic.Model.UserModel;
import com.example.enholic.repository.AuthRepository;
import com.example.enholic.repository.QuizRepository;


public class QuizViewModel extends AndroidViewModel implements QuizRepository.OnQuizLoad, AuthRepository.OnUserLoad {
    private MutableLiveData<QuizModel> quizMutableLiveData;
    private MutableLiveData<UserModel> userModelMutableLiveData;
    private QuizRepository repository;
    private AuthRepository authRepository;

    public MutableLiveData<QuizModel> getQuizMutableLiveData() {
        return quizMutableLiveData;
    }

    public MutableLiveData<UserModel> getUserModelMutableLiveData() {
        return userModelMutableLiveData;
    }

    public QuizViewModel(@NonNull Application application) {
        super(application);
        userModelMutableLiveData = new MutableLiveData<>();
        quizMutableLiveData = new MutableLiveData<>();
        repository = new QuizRepository(this);
        authRepository = new AuthRepository(application, this);
    }

    public void setQuizId(String quizId) {
        repository.setQuizID(quizId);
        repository.getQuiz();
    }

    public void loadUserProfile() {
        authRepository.loadUserProfile(authRepository.getCurrentUser().getUid());
    }

    public String getCurrentUserName(){
        return authRepository.getCurrentUser().getDisplayName();
    }


    public void UpdateEx(Long ExNumber, String oldLevel) {
        if (ExNumber >= 10) {
            if (oldLevel == "beginner") {authRepository.updateUserProfileLevel(authRepository.getCurrentUser().getUid(), "intermediate"); }
            else if(oldLevel == "intermediate") {authRepository.updateUserProfileLevel(authRepository.getCurrentUser().getUid(), "advanced"); }
            else {authRepository.updateUserProfileLevel(authRepository.getCurrentUser().getUid(), "beginner"); }
            ExNumber = Long.valueOf(0);
        }
        authRepository.updateUserProfileCurrentEx(authRepository.getCurrentUser().getUid(), ExNumber);
    }

    public void UpdatePoint(Long enPoint) {
        authRepository.updateUserProfileEnPoint(authRepository.getCurrentUser().getUid(), enPoint);
    }

    @Override
    public void onLoadQuiz(QuizModel quizModel) {
        quizMutableLiveData.setValue(quizModel);
    }

    @Override
    public void onError(Exception e) {
        Log.d("QuizError", "onError: " + e.getMessage());
    }

    @Override
    public void onLoad(UserModel userModel) {
        userModelMutableLiveData.setValue(userModel);
    }
}
