package com.example.hp.doubletapapp.database;

import android.os.Parcel;
import android.os.Parcelable;

public class WordEntity implements Parcelable {

    private int id;

    private String word;

    public String getWord() {
        return word;
    }

    public WordEntity(Parcel in) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WordEntity() {
        //default constructor
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(word);

    }

    public static final Parcelable.Creator<WordEntity> CREATOR
            = new Parcelable.Creator<WordEntity>() {

        @Override
        public WordEntity createFromParcel(Parcel in) {
            return new WordEntity(in);
        }

        @Override
        public WordEntity[] newArray(int size) {
            return new WordEntity[size];
        }
    };

}
