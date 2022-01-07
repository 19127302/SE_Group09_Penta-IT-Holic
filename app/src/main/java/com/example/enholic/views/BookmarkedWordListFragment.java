package com.example.enholic.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enholic.Model.UserWordModel;
import com.example.enholic.R;
import com.example.enholic.viewmodel.UserWordViewModel;

public class BookmarkedWordListFragment extends Fragment {

    private NavController navController;
    private UserWordViewModel viewModel;
    private LinearLayout bookmarkListLayout;
    private ImageButton backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookmarked_word_list, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.
                getInstance(getActivity().getApplication())).get(UserWordViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        bookmarkListLayout = view.findViewById(R.id.bookmarkWordListLayout);
        backButton = view.findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_bookmarkedWordListFragment_to_registeredHomepageFragment);
            }
        });
        loadWordList();
    }

    private void addListView(String word) {
        View bookmarkWordView = getLayoutInflater().inflate(R.layout.each_bookmarked_word, null,false);
        Button wordTextButton = bookmarkWordView.findViewById(R.id.word_title_button);
        wordTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookmarkedWordListFragmentDirections.ActionBookmarkedWordListFragmentToWordDetailsFragment action
                        = BookmarkedWordListFragmentDirections.actionBookmarkedWordListFragmentToWordDetailsFragment(word);

                navController.navigate(action);
            }

        });

        wordTextButton.setText(word);
        bookmarkListLayout.addView(bookmarkWordView);
    }

    private void loadWordList() {
        viewModel.getUserWordLiveData().observe(getViewLifecycleOwner(), new Observer<UserWordModel>() {
            @Override
            public void onChanged(UserWordModel userWordModel) {
                
                for(int i = 0; i < userWordModel.getWords().size(); i++) {
                    addListView(userWordModel.getWords().get(i));
                }
            }
        });
    }
}