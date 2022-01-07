package com.example.enholic.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enholic.Model.MeaningModel;
import com.example.enholic.Model.WordModel;
import com.example.enholic.R;
import com.example.enholic.viewmodel.UserBookmarkWordViewModel;
import com.example.enholic.viewmodel.WordViewModel;

public class WordDetailsFragment extends Fragment {
    private WordViewModel wordViewModel;
    private NavController navController;

    private String wordId;
    private LinearLayout meaningsListLayout;
    private TextView wordTextView;
    private ImageButton backButton;

    public WordDetailsFragment() {
        // Required empty public constructor
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public void initBeforeLoadWord() {
        Bundle bundle = this.getArguments();
        String wordId = bundle.getString("wordID");
        this.setWordId(wordId);
        Log.d("WordDetails", "Init before load" + " | WordID: " + wordId);
        wordViewModel.setWordId(wordId);
    }

    public static WordDetailsFragment newInstance() {
        WordDetailsFragment fragment = new WordDetailsFragment();

        //Log.d("WordDetails: ", "new instance with word: " + wordId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wordViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(WordViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_word_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        meaningsListLayout = view.findViewById(R.id.meaningsListLayout);
        backButton = view.findViewById(R.id.backButton);

        wordTextView = view.findViewById(R.id.wordTextView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_wordDetailsFragment_to_bookmarkedWordListFragment);
            }
        });
        initBeforeLoadWord();
        loadWord();
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

    private void loadWord() {
        wordViewModel.getWordMutableLiveData().observe(getViewLifecycleOwner(), new Observer<WordModel>() {
            @Override
            public void onChanged(WordModel wordModel) {
                if (!wordModel.getMeaning().isEmpty())
                {
                    wordTextView.setText(wordId);
                    wordTextView.setVisibility(View.VISIBLE);
                    meaningsListLayout.removeAllViews();

                    for(int i = 0; i < wordModel.getMeaning().size(); i++) {
                        addMeaningView(wordModel.getMeaning().get(i));
                    }
                }
            }
        });
    }
}