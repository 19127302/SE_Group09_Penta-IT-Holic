package com.example.enholic.views;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.enholic.Model.QuizModel;
import com.example.enholic.Model.UserModel;
import com.example.enholic.Model.WordModel;
import com.example.enholic.R;
import com.example.enholic.viewmodel.QuizViewModel;
import com.example.enholic.viewmodel.WordViewModel;
import com.google.firebase.firestore.auth.User;

import javax.annotation.Nullable;

public class QuizFragment extends Fragment {

    private QuizViewModel viewModel;
    private NavController navController;
    private Button option1BT, option2BT, option3BT, option4BT, backBT, nextExBT;
    private TextView questiontv;
    private Long index, enPoint;
    private String QuizID, level;
    private String CorrectAns = "";
    private TextView username, UserEPoint, UserLevel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(QuizViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    private void loadUserProfile() {
        viewModel.getUserModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                index = userModel.getCurrentEx() + 1;
                level = userModel.getLevel();
                enPoint = userModel.getEnPoint();
                QuizID = "ex" + level + index.toString();
                viewModel.setQuizId(QuizID);
                UserEPoint.setText(Long.toString(enPoint));
                UserLevel.setText(level);
                loadData();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel.loadUserProfile();
        option1BT = view.findViewById(R.id.option1BT);
        option2BT = view.findViewById(R.id.option2BT);
        option3BT = view.findViewById(R.id.option3BT);
        option4BT = view.findViewById(R.id.option4BT);
        backBT = view.findViewById(R.id.backBT);
        nextExBT = view.findViewById(R.id.nextExBT);
        questiontv = view.findViewById(R.id.quizquestion);
        username = view.findViewById(R.id.username_quiz);
        username.setText(viewModel.getCurrentUserName());
        UserEPoint = view.findViewById(R.id.enPoint);
        UserLevel = view.findViewById(R.id.UserLevel);
        loadUserProfile();

        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_quizFragment_to_registeredHomepageFragment);
            }
        });

        option1BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option2BT.setEnabled(false);
                option3BT.setEnabled(false);
                option4BT.setEnabled(false);
                verifyanswer(option1BT);
            }
        });

        option2BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1BT.setEnabled(false);
                option3BT.setEnabled(false);
                option4BT.setEnabled(false);
                verifyanswer(option2BT);
            }
        });

        option3BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option2BT.setEnabled(false);
                option1BT.setEnabled(false);
                option4BT.setEnabled(false);
                verifyanswer(option3BT);
            }
        });

        option4BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1BT.setEnabled(false);
                option2BT.setEnabled(false);
                option3BT.setEnabled(false);
                verifyanswer(option4BT);
            }
        });
        nextExBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.UpdateEx(index, level);
                loadUserProfile();
                navController.navigate(R.id.action_quizFragment_self);
            }
        });
    }

    private void loadData() {
        enableOption();
        loadQuizQuestion();
    }

    private void enableOption() {
        option1BT.setVisibility(View.VISIBLE);
        option2BT.setVisibility(View.VISIBLE);
        option3BT.setVisibility(View.VISIBLE);
        option4BT.setVisibility(View.VISIBLE);
        option1BT.setEnabled(true);
        option2BT.setEnabled(true);
        option3BT.setEnabled(true);
        option4BT.setEnabled(true);
    }

    private void loadQuizQuestion() {
        viewModel.getQuizMutableLiveData().observe(getViewLifecycleOwner(), new Observer<QuizModel>() {
            @Override
            public void onChanged(QuizModel quizModel) {
                questiontv.setText(quizModel.getQuestion());
                option1BT.setText(quizModel.getOptionA());
                option2BT.setText(quizModel.getOptionB());
                option3BT.setText(quizModel.getOptionC());
                option4BT.setText(quizModel.getOptionD());
                CorrectAns = quizModel.getAnswer();
            }
        });
    }

    private void resetOption() {
        nextExBT.setVisibility(View.INVISIBLE);
        nextExBT.setEnabled(false);
        option1BT.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.app_base1)));
        option2BT.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.app_base1)));
        option3BT.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.app_base1)));
        option4BT.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.app_base1)));
    }


    private void showNextBtn() {
        nextExBT.setVisibility(View.VISIBLE);
        nextExBT.setEnabled(true);
    }

    private void verifyanswer(Button button) {
        if (CorrectAns.equals(button.getText())) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.app_green)));
            enPoint += 50;
            viewModel.UpdatePoint(enPoint);
        } else {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.app_pink)));
        }
        showNextBtn();
    }
}