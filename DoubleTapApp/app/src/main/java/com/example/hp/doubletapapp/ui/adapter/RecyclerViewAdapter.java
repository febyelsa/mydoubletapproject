package com.example.hp.doubletapapp.ui.adapter;

import android.support.annotation.NonNull;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

    private List<WordEntity> mAllWords;
    private WordEntity selectedWord;
    private View.OnClickListener onItemSelect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EventBus.getDefault().post(new ClickEvents(EventsMessages.mEventItemSelectedForEditing,selectedWord));
        }
    };

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        View itemLayout;

        CustomViewHolder(View v) {
            super(v);
            mTextView = (TextView) itemView.findViewById(R.id.textViewWord);
            itemLayout =  itemView.findViewById(R.id.itemLayout);
            itemLayout.setOnClickListener(onItemSelect);
        }

    }

    public RecyclerViewAdapter(List<WordEntity> mAllWords) {
        this.mAllWords = mAllWords;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_item_list, viewGroup, false);
        CustomViewHolder vh = new CustomViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        Log.d("SqliteConnector", "binding  All values = " + mAllWords.get(i).getWord());
        Log.d("SqliteConnector", "binding   i = " + i);
        selectedWord = mAllWords.get(i);
        customViewHolder.mTextView.setText(mAllWords.get(i).getWord());
    }

    @Override
    public int getItemCount() {
        return mAllWords.size();
    }
}
