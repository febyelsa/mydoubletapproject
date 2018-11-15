package com.example.hp.doubletapapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.hp.doubletapapp.database.WordEntity;
import com.example.hp.doubletapapp.repository.WordRepository;

import java.util.List;

public class ListFragmentViewModel extends AndroidViewModel {

    WordRepository wordRepository;
    MutableLiveData<List<WordEntity>> mutableLiveData = new MutableLiveData<>();
    List<WordEntity> wordEntities;
    ObservableBoolean listEmptyObservable = new ObservableBoolean();


    public ListFragmentViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        listEmptyObservable .set(true);
    }

    public MutableLiveData<List<WordEntity>> getMutableWordsList() {
        wordEntities = wordRepository.getAllWords();
        mutableLiveData.setValue(wordEntities);

        return mutableLiveData;
    }

    public List<WordEntity> getAllWords() {
        setIsListEmpty(wordEntities == null || wordEntities.isEmpty());
        return wordEntities;
    }


    public boolean isListEmpty() {
        return listEmptyObservable .get();
    }

    public void setIsListEmpty(boolean isListEmpty) {
        listEmptyObservable.set(isListEmpty);;
        listEmptyObservable.notifyChange();
    }

}
