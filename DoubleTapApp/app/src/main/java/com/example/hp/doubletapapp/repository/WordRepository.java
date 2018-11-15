package com.example.hp.doubletapapp.repository;

import android.app.Application;

import com.example.hp.doubletapapp.database.SqliteConnector;
import com.example.hp.doubletapapp.database.WordEntity;

import java.util.List;

public class WordRepository {

    private List<WordEntity> mAllWords;
    SqliteConnector mSqliteConnector;

    public WordRepository(Application application) {
        mSqliteConnector = new SqliteConnector(application);
    }

    public List<WordEntity> getAllWords() {
        mAllWords = mSqliteConnector.getAllWords();
        return mAllWords;
    }

    public void insertWord(String word) {
        mSqliteConnector.insertWord(word);
    }
    public void updateWord(WordEntity word) {
        mSqliteConnector.updateWord(word);
    }

}
