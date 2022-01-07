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

import com.example.enholic.Model.RewardModel;
import com.example.enholic.Model.UserModel;
import com.example.enholic.R;
import com.example.enholic.viewmodel.RewardViewModel;
import com.example.enholic.viewmodel.UserRewardViewModel;
import com.example.enholic.viewmodel.UserWordViewModel;


public class ReceivedRewardList extends Fragment {
    private UserRewardViewModel viewModel2;
    private RewardViewModel viewModel;
    private NavController navController;
    private TextView totalPrize, userEnp, username;
    private LinearLayout rewardLayout;
    private ImageButton backButton;
    private Long enPoint, total;

    public ReceivedRewardList() {
        // Required empty public constructor
    }

    private void loadUserProfile() {
        viewModel.getUserModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                enPoint = userModel.getEnPoint();
                userEnp.setText(" " + Long.toString(enPoint));
                total = userModel.getsGb() + userModel.getbGb();
                totalPrize.setText(" " + Long.toString(total));
                //viewModel.setRewardId(rewardID);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_received_reward_list, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.
                getInstance(getActivity().getApplication())).get(RewardViewModel.class);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        //viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
        //     .getInstance(getActivity().getApplication())).get(RewardViewModel.class);
        viewModel.loadUserProfile();
        backButton = view.findViewById(R.id.rewardlistReturn);
        loadUserProfile();
        userEnp = view.findViewById(R.id.rewardEnpoint);
        totalPrize = view.findViewById((R.id.rewardNum));
        username = view.findViewById(R.id.rewardListUserName);
        username.setText(viewModel.getCurrentUser().getDisplayName());
        rewardLayout = view.findViewById(R.id.rewardLayout);
        rewardLayout.removeAllViews();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_receivedRewardList_to_rewardFragment);
            }
        });
        loadReward();
        loadReward2();
    }

    private void addListView(String reward) {
        View rewardListView = getLayoutInflater().inflate(R.layout.each_reward, null, false);
        Button rewardTextButton = rewardListView.findViewById(R.id.reward_title_button);
        rewardTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceivedRewardListDirections.ActionReceivedRewardListToRewardSmallFragment action
                        = ReceivedRewardListDirections.actionReceivedRewardListToRewardSmallFragment(reward);
                navController.navigate(action);
            }
        });
        rewardTextButton.setText(reward);
        rewardLayout.addView(rewardListView);
    }

    private void addListView2(String reward) {
        View rewardListView = getLayoutInflater().inflate(R.layout.each_reward, null, false);
        Button rewardTextButton = rewardListView.findViewById(R.id.reward_title_button);
        rewardTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceivedRewardListDirections.ActionReceivedRewardListToRewardHugeFragment action
                        = ReceivedRewardListDirections.actionReceivedRewardListToRewardHugeFragment(reward);
                navController.navigate(action);
            }
        });
        rewardTextButton.setText(reward);
        rewardLayout.addView(rewardListView);
    }

    private void loadReward() {
        viewModel.getUserModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                for (Long i = 1L; i <= userModel.getsGb(); i++) {
                    addListView("smallgiftbox" + i.toString());
                }
            }
        });
    }

    private void loadReward2() {
        viewModel.getUserModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                for (Long i = 1L; i <= userModel.getbGb(); i++) {
                    addListView2("hugegiftbox" + i.toString());
                }
            }
        });
    }

    private void removeListView() {
        View rewardListView = getLayoutInflater().inflate(R.layout.each_reward, null,false);
        viewModel.getUserModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                for (int i = 0; i < userModel.getsGb() + userModel.getbGb(); i++) {
                    rewardLayout.removeViewAt(i);
                }
            }
        });
    }
}
