package com.example.enholic.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.enholic.Model.QuizModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class QuizRepository {

    private FirebaseFirestore firebaseFirestore;
    private String quizID;
    private OnQuizLoad onQuizLoad;

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public QuizRepository(OnQuizLoad onQuizLoad){
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onQuizLoad = onQuizLoad;
    }

    public void getQuiz(){
        firebaseFirestore.collection("Exercise").document(quizID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    onQuizLoad.onLoad(task.getResult().toObject(QuizModel.class));
            }else{
                    onQuizLoad.onError(task.getException());
                }

            }
        });
    }

    public interface OnQuizLoad{
        void onLoad(QuizModel quizModel);
        void onError(Exception e);
    }
}
