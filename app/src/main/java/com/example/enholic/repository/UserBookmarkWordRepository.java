package com.example.enholic.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserBookmarkWordRepository {
    private FirebaseFirestore firebaseFirestore;
    private String userId;
    private String wordId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public UserBookmarkWordRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public UserBookmarkWordRepository(String userId, String wordId) {
        this.userId = userId;
        this.wordId = wordId;

        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void saveBookmarkWord() {
        Log.d("BookmarkRepo", "UserID: " + userId);
        Log.d("BookmarkRepo", "WordID: " + wordId);

        firebaseFirestore.collection("User_Word").document(userId)
                .update("word", FieldValue.arrayUnion(wordId));
    }
}
