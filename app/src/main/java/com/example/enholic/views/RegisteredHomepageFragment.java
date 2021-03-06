package com.example.enholic.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.enholic.R;
import com.example.enholic.viewmodel.AuthViewModel;

public class RegisteredHomepageFragment extends Fragment {

    private AuthViewModel viewModel;
    private NavController navController;
    private TextView username;
    private ImageButton signOutBtn, lookUpBtn, bookmarkBtn, quizBtn, translateBtn, rwBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registered_homepage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);
        navController = Navigation.findNavController(view);
        username = view.findViewById(R.id.username_RegisteredHomepage);
        username.setText(viewModel.getCurrentUser().getDisplayName());
        signOutBtn = view.findViewById(R.id.signOut_RegisteredHomepage);
        lookUpBtn = view.findViewById(R.id.lookUp_RegisteredHomepage);
        bookmarkBtn = view.findViewById(R.id.bookmark_RegisteredHomepage);
        quizBtn = view.findViewById(R.id.exercise_RegisteredHomepage);
        translateBtn = view.findViewById(R.id.translate_RegisteredHomepage);
        rwBtn = view.findViewById(R.id.reward_RegisteredHomepage);

        lookUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registeredHomepageFragment_to_lookUpWordFragment);
            }
        });

        quizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registeredHomepageFragment_to_quizFragment);
            }
        });

        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registeredHomepageFragment_to_bookmarkedWordListFragment);
            }
        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.signOut();
                navController.navigate(R.id.action_registeredHomepageFragment_to_signInFragment);
            }
        });

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registeredHomepageFragment_to_translateFragment);
            }
        });

        rwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registeredHomepageFragment_to_rewardFragment);
            }
        });
    }
}