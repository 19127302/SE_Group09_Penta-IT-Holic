package com.example.enholic.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enholic.Model.MeaningModel;
import com.example.enholic.Model.WordModel;
import com.example.enholic.R;
import com.example.enholic.viewmodel.WordViewModel;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

public class LookUpWordFragment extends Fragment {

    private WordViewModel viewModel;
    private NavController navController;

    private EditText wordEditText;
    private ImageButton searchButton;
    private TextView wordTextView;
    private ImageButton backButton;

    private String wordId;

    LinearLayout meaningsListLayout;

    public LookUpWordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(WordViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_look_up_word, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        wordEditText = view.findViewById(R.id.wordEditText);
        searchButton = view.findViewById(R.id.searchWordButton);
        wordTextView = view.findViewById(R.id.wordTextView);

        meaningsListLayout = view.findViewById(R.id.meaningsListLayout);
        backButton = view.findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_lookUpWordFragment_to_registeredHomepageFragment);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordId = wordEditText.getText().toString();
                viewModel.setWordId(wordId);

                loadData();
            }
        });

    }
    private void addMeaningView(MeaningModel meaning) {
        View meaningView = getLayoutInflater().inflate(R.layout.each_meaning, null,false);
        TextView wordClassTextView = meaningView.findViewById(R.id.wordClassTextView);
        TextView definitionTextView = meaningView.findViewById(R.id.definitionTextView);
        TextView exampleTextView = meaningView.findViewById(R.id.exampleTextView);

        wordClassTextView.setText(meaning.getWord_class());
        definitionTextView.setText(meaning.getDefinition());
        // Hiển thị cái bullet
        String example = "\u2022\t" + meaning.getExample();
        exampleTextView.setText(example);

        meaningsListLayout.addView(meaningView);
    }

    private void loadData() {
        loadWord();
    }

    private void loadWord() {
        viewModel.getWordMutableLiveData().observe(getViewLifecycleOwner(), new Observer<WordModel>() {
            @Override
            public void onChanged(WordModel wordModel) {
                wordTextView.setText(wordId);
                meaningsListLayout.removeAllViews();

                for(int i = 0; i < wordModel.getMeaning().size(); i++) {
                    addMeaningView(wordModel.getMeaning().get(i));
                }
            }
        });
    }
}