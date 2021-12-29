package com.example.enholic.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enholic.Model.WordModel;
import com.example.enholic.R;
import com.example.enholic.viewmodel.WordViewModel;

import java.util.List;

import javax.annotation.Nullable;

public class LookUpWordFragment extends Fragment {

    private WordViewModel viewModel;
    private NavController navController;

    private EditText wordEditText;
    private ImageButton searchButton;
    private TextView wordTextView;
    private TextView wordClassTextView;
    private TextView definitionTextView;
    private TextView exampleTextView;

    private String wordId;

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
        wordClassTextView = view.findViewById(R.id.wordClass1TextView);
        definitionTextView = view.findViewById(R.id.definition1TextView);
        exampleTextView = view.findViewById(R.id.example1TextView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordId = wordEditText.getText().toString();
                viewModel.setWordId(wordId);

                Toast.makeText(getContext(), wordId, Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void onSearchButtonClick(View view) {
        wordId = wordEditText.getText().toString();
        viewModel.setWordId(wordId);

        loadData();
    }
    private void loadData() {
        loadWord();
    }

    private void loadWord() {
        viewModel.getWordMutableLiveData().observe(getViewLifecycleOwner(), new Observer<WordModel>() {
            @Override
            public void onChanged(WordModel wordModel) {
                wordTextView.setText(wordId);
                wordClassTextView.setText(wordModel.getMeaning().get(0).getWord_class());
                definitionTextView.setText(wordModel.getMeaning().get(0).getDefinition());
                exampleTextView.setText(wordModel.getMeaning().get(0).getExample());


            }
        });
    }
}