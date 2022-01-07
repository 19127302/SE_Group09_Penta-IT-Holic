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
import android.widget.Toast;

import com.example.enholic.Model.QuizModel;
import com.example.enholic.Model.RewardModel;
import com.example.enholic.Model.UserModel;
import com.example.enholic.R;
import com.example.enholic.viewmodel.AuthViewModel;
import com.example.enholic.viewmodel.RewardViewModel;

import javax.annotation.Nullable;

public class RewardFragment extends Fragment {

    private RewardViewModel viewModel;
    //private ReceivedRewardListViewModel receivedRewardListViewModel;
    private NavController navController;
    private TextView username, userEnp;
    private String srewardID, brewardID;
    private Long indexs, indexb, enPoint ;
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

    private void loadUserProfile() {
        viewModel.getUserModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                enPoint = userModel.getEnPoint();
                userEnp.setText(" " + Long.toString(enPoint));
                indexs = userModel.getsGb() + 1;
                indexb = userModel.getbGb() + 1;
                srewardID = "smallgiftbox"  + indexs.toString();
                brewardID = "biggiftbox" + indexb.toString();
                //viewModel.setRewardId(rewardID);
            }
        });
    }

    private void loadSmallReward() {
        viewModel.getRewardMutableLiveData().observe(getViewLifecycleOwner(), new Observer<RewardModel>() {
            @Override
            public void onChanged(RewardModel rewardModel) {
                viewModel.setRewardId(srewardID);
            }
        });
    }

    private void loadBigReward() {
        viewModel.getRewardMutableLiveData().observe(getViewLifecycleOwner(), new Observer<RewardModel>() {
            @Override
            public void onChanged(RewardModel rewardModel) {
                viewModel.setRewardId(brewardID);
            }
        });
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(RewardViewModel.class);
        viewModel.loadUserProfile();
        smallGb = view.findViewById(R.id.smallGBoxButton);
        bigGb = view.findViewById(R.id.hugeGBoxButton);
        backBT = view.findViewById(R.id.navReturn);
        loadUserProfile();
        userEnp = view.findViewById(R.id.enpoint_textview);
        myReward = view.findViewById(R.id.myReward);
        username = view.findViewById(R.id.userRewardname);
        username.setText(viewModel.getCurrentUser().getDisplayName());

        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_rewardFragment_to_registeredHomepageFragment);
            }
        });

        smallGb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enPoint > 1000 && indexs <= 2) {
                    enPoint -= 1000;
                    viewModel.UpdatePoint(enPoint);
                    indexs +=0;
                    viewModel.UpdatesGb(indexs);
                    navController.navigate(R.id.action_rewardFragment_to_rewardDetails);

                }
                else if (enPoint > 1000 && indexs > 2){
                    Toast.makeText(getContext(), "You already have full small prizes. Wait for more updates ", Toast.LENGTH_SHORT).show();}
                else
                    Toast.makeText(getContext(), "Not enough enPoints: ", Toast.LENGTH_SHORT).show();
            }
        });

        bigGb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enPoint > 10000 && indexb < 3) {
                    enPoint -= 10000;
                    viewModel.UpdatePoint(enPoint);
                    indexb +=0;
                    viewModel.UpdatebGb(indexb);
                    navController.navigate(R.id.action_rewardFragment_to_rewardGoldenFragment);

                   // receivedRewardListViewModel.setRewardId(rewardID);
                }
                else if (enPoint > 10000 && indexb >= 3){
                    Toast.makeText(getContext(), "You already have full big prizes. Wait for more updates ", Toast.LENGTH_SHORT).show();}
                else
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

