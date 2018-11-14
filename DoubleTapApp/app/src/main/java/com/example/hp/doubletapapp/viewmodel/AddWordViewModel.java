package com.example.hp.doubletapapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;


import com.example.hp.doubletapapp.R;
import com.example.hp.doubletapapp.database.WordEntity;
import com.example.hp.doubletapapp.events.ClickEvents;
import com.example.hp.doubletapapp.events.EventsMessages;
import com.example.hp.doubletapapp.repository.WordRepository;

import org.greenrobot.eventbus.EventBus;

public class AddWordViewModel extends AndroidViewModel {

    WordRepository wordRepository;
    String word = "";
    ObservableBoolean showProgress = new ObservableBoolean();
    private WordEntity selectedItem = null;

    public AddWordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        showProgress.set(false);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setSelectedItem(WordEntity selectedItem) {
        this.selectedItem = selectedItem;

        if (selectedItem != null)
            setWord(selectedItem.getWord());
    }

    public WordEntity getSelectedItem() {
        return selectedItem;
    }

    public boolean showProgress() {
        return showProgress.get();
    }

    public void saveButtonClick() {

        if (null != word && !word.isEmpty()) {

            Log.d("SqliteConnector", "saveButtonClick" + word);
            new addWordToDb().execute();
            return;
        }

        Toast.makeText(getApplication(), R.string.word_empty_msg, Toast.LENGTH_LONG).show();

    }

    private class addWordToDb extends AsyncTask<WordEntity, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress.set(true);
        }

        @Override
        protected String doInBackground(WordEntity... wordEntities) {
            if (selectedItem!=null && !selectedItem.getWord().isEmpty()) {
                wordRepository.updateWord(selectedItem);
                return null;
            }
            wordRepository.insertWord(word);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            showProgress.set(false);
            EventBus.getDefault().post(new ClickEvents(EventsMessages.mEventNewWordSaveClicked, null));
        }
    }


}
