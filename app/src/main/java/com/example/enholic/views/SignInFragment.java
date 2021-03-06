package com.example.enholic.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.enholic.R;
import com.example.enholic.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {

    private AuthViewModel viewModel;
    private NavController navController;
    private EditText editEmail, editPass;
    private Button signUpBtn, signInBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AuthViewModel.class);
        navController = Navigation.findNavController(view);

        editEmail = view.findViewById(R.id.inputEmail_SignIn);
        editPass = view.findViewById(R.id.inputPassword_SignIn);
        signUpBtn = view.findViewById(R.id.registerBtn_SignIn);
        signInBtn = view.findViewById(R.id.signInBtn_SignIn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String pass = editPass.getText().toString();
                if(!email.isEmpty() && !pass.isEmpty())
                {
                    viewModel.signIn(email, pass);
                    viewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser firebaseUser) {
                            if(firebaseUser != null) {
                                navController.navigate(R.id.action_signInFragment_to_registeredHomepageFragment);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getContext(), "Please enter both email and password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}