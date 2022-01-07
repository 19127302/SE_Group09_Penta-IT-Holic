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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.enholic.Model.RewardModel;
import com.example.enholic.Model.UserModel;
import com.example.enholic.R;
import com.example.enholic.viewmodel.RewardViewModel;

import javax.annotation.Nullable;

public class RewardDetails extends Fragment {
    private RewardViewModel viewModel;
    //private ReceivedRewardListViewModel receivedRewardListViewModel;
    private NavController navController;
    private TextView idiom, desc;
    private ImageButton backBT;
    private String srewardID, brewardID = "";
    private Long indexs;

    public RewardDetails() {
        // Required empty public constructor
    }

    public static RewardDetails newInstance() {
        RewardDetails fragment = new RewardDetails();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reward_details, container, false);
    }

    private void loadUserProfile() {
        viewModel.getUserModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                indexs = userModel.getsGb();
                brewardID = "smallgiftbox"  + indexs.toString();
                viewModel.setRewardId(brewardID);
                loadReward();
            }
        });
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(RewardViewModel.class);
        viewModel.loadUserProfile();
        backBT = view.findViewById(R.id.backRewardbt);
        idiom = view.findViewById(R.id.idioms);
        desc = view.findViewById(R.id.rewardDescryption1);

        loadUserProfile();
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_rewardDetails_to_rewardFragment);
            }
        });
    }

    private void loadReward() {
        viewModel.getRewardMutableLiveData().observe(getViewLifecycleOwner(), new Observer<RewardModel>() {
            @Override
            public void onChanged(RewardModel rewardModel) {
                idiom.setText(rewardModel.getIdiom());
                desc.setText(rewardModel.getDescription());
            }
        });
    }
}