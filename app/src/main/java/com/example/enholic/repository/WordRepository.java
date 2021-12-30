package com.example.enholic.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.enholic.Model.MeaningModel;
import com.example.enholic.Model.WordModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordRepository {

    private FirebaseFirestore firebaseFirestore;
    private String wordId;
    private OnWordLoad onWordLoad;
    private WordModel wordModel;

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public WordRepository(OnWordLoad onWordLoad) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onWordLoad = onWordLoad;
    }

    public WordModel getWordModel() {
        return wordModel;
    }

    public void getWord() {
        firebaseFirestore.collection("Words").document(wordId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Log.d("WordRepository", documentSnapshot.toString());

                    ArrayList<HashMap<String, String>> list_meaning = (ArrayList<HashMap<String, String>>) documentSnapshot.get("meanings");//toObjects(WordModel.class.getDeclaredField("meaning"));

                    ArrayList<MeaningModel> meanings = new ArrayList<>();
                    for(int i = 0; i < list_meaning.size(); i++) {
                        HashMap<String, String> meaning_temp = list_meaning.get(i);
                        meanings.add(new MeaningModel(meaning_temp.get("definition"), meaning_temp.get("class"), meaning_temp.get("example")));
                    }

                    WordModel wordModel_temp = new WordModel();
                    wordModel_temp.setMeaning(meanings);
                    onWordLoad.onLoad(wordModel_temp);
                }
                else {
                    onWordLoad.onError(new Exception("No data"));
                }

            }
        });
    }

    public interface OnWordLoad {
        void onLoad(WordModel wordModel);
        void onError(Exception e);
    }

}
