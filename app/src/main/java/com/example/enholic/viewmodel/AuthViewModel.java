package com.example.enholic.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.enholic.Model.UserModel;
import com.example.enholic.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel implements AuthRepository.OnUserLoad {

    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private FirebaseUser currentUser;
    private AuthRepository authRepository;

    private UserModel userModel;
    private MutableLiveData<UserModel> userModelMutableLiveData;

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<UserModel> getUserModelMutableLiveData() {
        return userModelMutableLiveData;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application, this);
        currentUser = authRepository.getCurrentUser();
        firebaseUserMutableLiveData = authRepository.getFirebaseUserMutableLiveData();

        userModelMutableLiveData = new MutableLiveData<>();
    }

    public void signUp(String email, String pass, String name) {
        authRepository.signUp(email, pass, name);
    }

    public void signIn(String email, String pass) {
        authRepository.signIn(email, pass);
    }

    public void signOut() {
        authRepository.signOut();
    }

    @Override
    public void onLoad(UserModel userModel) {
        userModelMutableLiveData.setValue(userModel);
    }

    @Override
    public void onError(Exception e) {
        Log.d("UserError", "onError: " + e.getMessage());
    }
}
