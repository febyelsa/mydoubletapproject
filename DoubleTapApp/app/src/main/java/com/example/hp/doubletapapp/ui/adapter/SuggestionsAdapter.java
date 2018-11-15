package com.example.hp.doubletapapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.doubletapapp.R;
import com.example.hp.doubletapapp.database.WordEntity;
import com.example.hp.doubletapapp.events.ClickEvents;
import com.example.hp.doubletapapp.events.EventsMessages;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SuggestionsAdapter extends  RecyclerView.Adapter<SuggestionsAdapter.CustomViewHolder> {

    private List<WordEntity> suggestionsList;
    private Context context;
    private WordEntity selectedWord;

    private View.OnClickListener onItemSelect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendMessage(selectedWord.getWord());
        }
    };

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        CustomViewHolder(View v) {
            super(v);
            mTextView = (TextView) itemView.findViewById(R.id.keyboardTextView);
            mTextView.setOnClickListener(onItemSelect);
        }
    }

    public SuggestionsAdapter(Context context,List<WordEntity> suggestionsList) {
        this.context= context;
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

        selectedWord = suggestionsList.get(position);
        holder.mTextView.setText(suggestionsList.get(position).getWord());
    }

    @Override
    public int getItemCount() {
        return suggestionsList.size();
    }

    private void sendMessage(String message) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
