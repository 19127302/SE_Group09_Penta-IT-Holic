package com.example.enholic.repository;

import android.util.Log;

import com.example.enholic.Model.UserWordModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserWordRepository {
    private FirebaseFirestore firebaseFirestore;
    private String user_id;
    private UserWordRepository.OnUserWordLoad onUserWordLoad;
    private UserWordModel userwordModel;
    private UserWordModel UserWordModel;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public UserWordRepository(UserWordRepository.OnUserWordLoad onUserWordLoad) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onUserWordLoad = onUserWordLoad;
    }

    public UserWordModel getUserWordModel() {
        return UserWordModel;
    }

    public void getWord() {
        // AuthRepository firebaseAuth = null;
        // user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("User_Word").document(user_id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Log.d("UserWordRepository", documentSnapshot.toString());

                    //toObjects(WordModel.class.getDeclaredField("meaning"));
                    ArrayList<String> word_list = (ArrayList<String>) documentSnapshot.get("word");

                    UserWordModel user_word_Model_temp = new UserWordModel(word_list);
                    onUserWordLoad.onLoad(user_word_Model_temp);
                }
                else {
                    onUserWordLoad.onError(new Exception("No data"));
                }
            }
        });
    }

    public interface OnUserWordLoad {
        void onLoad(UserWordModel userwordModel);
        void onError(Exception e);
    }
}
