package com.example.enholic.views;

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
import com.example.enholic.R;
import com.example.enholic.viewmodel.QuizViewModel;
import com.example.enholic.viewmodel.WordViewModel;
import com.google.firebase.firestore.auth.User;

import javax.annotation.Nullable;

public class QuizFragment extends Fragment implements View.OnClickListener {

    private QuizViewModel viewModel;
    private NavController navController;
    private Button option1BT, option2BT, option3BT, option4BT, backBT, nextExBT;
    private TextView questiontv;
    private int index = 1;
    private String QuizID = "exbeginner1";
    private String CorrectAns ="";

    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(QuizViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        option1BT = view.findViewById(R.id.option1BT);
        option2BT = view.findViewById(R.id.option2BT);
        option3BT = view.findViewById(R.id.option3BT);
        option4BT = view.findViewById(R.id.option4BT);
        backBT = view.findViewById(R.id.backBT);
        nextExBT = view.findViewById(R.id.nextExBT);
        questiontv = view.findViewById(R.id.quizquestion);
        viewModel.setQuizId(QuizID);
        option1BT.setOnClickListener(this);
        option2BT.setOnClickListener(this);
        option3BT.setOnClickListener(this);
        option4BT.setOnClickListener(this);
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_quizFragment_to_registeredHomepageFragment);
            }
        });


        loadData();
    }

    private void loadData(){
        enableOption();;
        loadQuizQuestion();
    }

    private void enableOption(){
        option1BT.setVisibility(View.VISIBLE);
        option2BT.setVisibility(View.VISIBLE);
        option3BT.setVisibility(View.VISIBLE);
        option4BT.setVisibility(View.VISIBLE);
        option1BT.setEnabled(true);
        option2BT.setEnabled(true);
        option3BT.setEnabled(true);
        option4BT.setEnabled(true);
        nextExBT.setVisibility(View.INVISIBLE);

    }
    private void loadQuizQuestion(){
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.option1BT:
                verifyanswer(option1BT);
                break;
            case R.id.option2BT:
                verifyanswer(option2BT);
                break;
            case R.id.option3BT:
                verifyanswer(option3BT);
                break;
            case R.id.option4BT:
                verifyanswer(option4BT);
                break;
            case R.id.nextExBT:
                index++;
                loadData();
                resetOption();
                break;
        }
    }

    private void resetOption() {
        option1BT.setBackground(ContextCompat.getDrawable(getContext(), R.color.app_base1));
        option2BT.setBackground(ContextCompat.getDrawable(getContext(), R.color.app_base1));
        option3BT.setBackground(ContextCompat.getDrawable(getContext(), R.color.app_base1));
        option4BT.setBackground(ContextCompat.getDrawable(getContext(), R.color.app_base1));

    }

    private void showNextBtn() {
            nextExBT.setVisibility(View.VISIBLE);
            nextExBT.setEnabled(true);
    }

    private void verifyanswer(Button button){
        if(CorrectAns.equals(button.getText())){
            button.setBackground(ContextCompat.getDrawable(getContext(),R.color.app_green));
        }else {
            button.setBackground(ContextCompat.getDrawable(getContext(),R.color.app_pink));
        }
        showNextBtn();

    }

}