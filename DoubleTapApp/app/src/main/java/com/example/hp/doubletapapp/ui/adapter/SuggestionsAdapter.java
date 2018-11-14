package com.example.hp.doubletapapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.doubletapapp.R;
import com.example.hp.doubletapapp.database.WordEntity;

import java.util.List;

public class SuggestionsAdapter extends  RecyclerView.Adapter<SuggestionsAdapter.CustomViewHolder> {

    private List<WordEntity> suggestionsList;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        CustomViewHolder(View v) {
            super(v);
            mTextView = (TextView) itemView.findViewById(R.id.keyboardTextView);
        }
    }

    public SuggestionsAdapter(List<WordEntity> suggestionsList) {
        this.suggestionsList = suggestionsList;
    }

    @Override
    public SuggestionsAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggestions_item, parent, false);
        CustomViewHolder vh = new CustomViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(SuggestionsAdapter.CustomViewHolder holder, int position) {
        holder.mTextView.setText(suggestionsList.get(position).getWord());
    }

    @Override
    public int getItemCount() {
        return suggestionsList.size();
    }
}
