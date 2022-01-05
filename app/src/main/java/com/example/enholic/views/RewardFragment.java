package com.example.enholic.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.enholic.R;
import com.example.enholic.viewmodel.RewardViewModel;

public class RewardFragment extends Fragment {

    private RewardViewModel viewModel;
    private NavController navController;
    private TextView username;
    private ImageButton signOutBtn, lookUpBtn, bookmarkBtn, quizBtn, translateBtn, rwBtn;

    public RewardFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reward, container, false);
    }
}