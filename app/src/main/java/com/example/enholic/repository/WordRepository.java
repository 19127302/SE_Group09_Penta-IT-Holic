package com.example.enholic.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.enholic.Model.WordModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class WordRepository {

    private FirebaseFirestore firebaseFirestore;
    private String wordId;
    private OnWordLoad onWordLoad;

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public WordRepository(OnWordLoad onWordLoad) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onWordLoad = onWordLoad;
    }

    public void getWords() {
        firebaseFirestore.collection("Words").document(wordId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    onWordLoad.onLoad(task.getResult().toObject(WordModel.class));
                }
                else {
                    onWordLoad.onError(task.getException());
                }
            }
        });
    }

    public interface OnWordLoad {
        void onLoad(WordModel wordModel);
        void onError(Exception e);
    }

}
