package com.example.enholic.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enholic.R;
import com.example.enholic.viewmodel.AuthViewModel;
import com.example.enholic.viewmodel.RewardViewModel;

import javax.annotation.Nullable;

public class RewardFragment extends Fragment {

    private RewardViewModel viewModel;
    //private ReceivedRewardListViewModel receivedRewardListViewModel;
    private NavController navController;
    private TextView username, userEnp;
    private String rewardID;
    private Long enPoint = 1020L;
    private ImageButton smallGb, bigGb, myReward, backBT;

    public RewardFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reward, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(RewardViewModel.class);
        //viewModel.loadUserProfile();
        smallGb = view.findViewById(R.id.smallGBoxButton);
        bigGb = view.findViewById(R.id.hugeGBoxButton);
        backBT = view.findViewById(R.id.navReturn);
        userEnp = view.findViewById(R.id.enpoint_textview);
        myReward = view.findViewById(R.id.myReward);
        username = view.findViewById(R.id.userRewardname);
        //loadUserProfile();

        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_rewardFragment_to_registeredHomepageFragment);
            }
        });

        smallGb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enPoint > 1000) {
                    navController.navigate(R.id.action_rewardFragment_to_rewardDetails);
                } else
                    Toast.makeText(getContext(), "Not enough enPoints: ", Toast.LENGTH_SHORT).show();
            }
        });

        bigGb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enPoint > 10000) {
                    navController.navigate(R.id.action_rewardFragment_to_rewardDetails);
                   // receivedRewardListViewModel.setRewardId(rewardID);
                    //viewModel.UpdatePoint(enPoint);
                } else

                    Toast.makeText(getContext(), "Not enough enPoints: ", Toast.LENGTH_SHORT).show();

            }
        });
        myReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    navController.navigate(R.id.action_rewardFragment_to_receivedRewardList);
            }
        });
    }
}