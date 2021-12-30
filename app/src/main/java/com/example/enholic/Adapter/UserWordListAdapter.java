package com.example.enholic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enholic.Model.UserWordModel;
import com.example.enholic.R;

import java.util.List;

public class UserWordListAdapter extends RecyclerView.Adapter <UserWordListAdapter.UserWordListViewHolder> {

    private List<UserWordModel> userWordModel;

    public void setUserWordModel(List<UserWordModel> userWordModel) {
        this.userWordModel = userWordModel;
    }

    @NonNull
    @Override
    public UserWordListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_bookmarked_word, parent, false);
        return new UserWordListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserWordListViewHolder holder, int position) {
        UserWordModel model = userWordModel.get(position);
        holder.word.setText((CharSequence) model.getWords());
    }

    @Override
    public int getItemCount() {
        if (userWordModel == null) {
            return 0;
        }
        else {
            return userWordModel.size();
        }
    }

    public class UserWordListViewHolder extends RecyclerView.ViewHolder {
        private TextView word;
        public UserWordListViewHolder(@NonNull View itemView) {
            super(itemView);

            word = itemView.findViewById(R.id.word_title);
        }
    }

}
