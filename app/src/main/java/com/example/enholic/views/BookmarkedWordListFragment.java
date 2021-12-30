package com.example.enholic.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.enholic.Adapter.UserWordListAdapter;
import com.example.enholic.Model.MeaningModel;
import com.example.enholic.Model.UserWordModel;
import com.example.enholic.Model.WordModel;
import com.example.enholic.R;
import com.example.enholic.viewmodel.AuthViewModel;
import com.example.enholic.viewmodel.UserWordViewModel;
import com.example.enholic.viewmodel.WordViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookmarkedWordListFragment extends Fragment {

    private RecyclerView recyclerView;
    private NavController navController;
    private UserWordViewModel viewModel;
    private UserWordListAdapter adapter;

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

        recyclerView = view.findViewById(R.id.userWordRecyclerView);
        navController = Navigation.findNavController(view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserWordListAdapter();

        recyclerView.setAdapter(adapter);

        viewModel.getUserWordLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserWordModel>>() {
            @Override
            public void onChanged(List<UserWordModel> userWordModels) {
                adapter.setUserWordModel(userWordModels);
                adapter.notifyDataSetChanged();
            }
        });
    }
}