package com.example.enholic.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enholic.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class TranslateFragment extends Fragment {
    private NavController navController;
    private ImageButton backButton, swapLangButton;
    private Button submitButton, clearButton;
    private EditText fromLangEditText;
    private TextView toLangResultTextView, toLangTextView, fromLangTextView;

    private Translator translatorEngVie, translatorVieEng;
    private int langState; //0 means translate from eng to vie; 1 means translate to vie to eng

    public TranslateFragment() {
        // Required empty public constructor
    }


    public static TranslateFragment newInstance() {
        TranslateFragment fragment = new TranslateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        backButton = view.findViewById(R.id.backButton);
        submitButton = view.findViewById(R.id.submitButton);
        swapLangButton = view.findViewById(R.id.swapLangButton);
        clearButton = view.findViewById(R.id.clearButton);

        fromLangEditText = view.findViewById(R.id.fromLangEditText);
        toLangResultTextView = view.findViewById(R.id.toLangResultTextView);
        fromLangTextView = view.findViewById(R.id.fromLangTextView);
        toLangTextView = view.findViewById(R.id.toLangTextView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_translateFragment_to_registeredHomepageFragment);
            }
        });

        TranslatorOptions optionsEngVie =
                new TranslatorOptions .Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.VIETNAMESE)
                        .build();

        translatorEngVie = Translation.getClient(optionsEngVie);

        TranslatorOptions optionsVieEng =
                new TranslatorOptions .Builder()
                        .setSourceLanguage(TranslateLanguage.VIETNAMESE)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build();

        translatorVieEng = Translation.getClient(optionsVieEng);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = fromLangEditText.getText().toString();
                if (langState == 0)
                {
                    downloadModal(translatorEngVie, string);
                }
                else {
                    downloadModal(translatorVieEng, string);
                }
            }
        });

        swapLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (langState == 0)
                {
                    langState = 1;
                    fromLangTextView.setText("Vietnamese");
                    toLangTextView.setText("English");
                }
                else {
                    langState = 0;
                    fromLangTextView.setText("English");
                    toLangTextView.setText("Vietnamese");
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromLangEditText.setText("");
            }
        });
    }

    private void downloadModal(Translator translator, String input) {
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi().build();
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                translateLanguage(translator, input);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to set up translator. Cannot translate.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void translateLanguage(Translator translator, String input) {
        translator.translate(input).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                toLangResultTextView.setText(s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to translate", Toast.LENGTH_SHORT).show();
            }
        });
    }
}