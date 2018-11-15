package com.example.hp.doubletapapp.database;
import android.content.Context;

//@Database(entities = {WordEntity.class}, version = 1)
public abstract class WordDataBase {

    public abstract WordDao wordDao();
    private static  WordDataBase INSTANCE;
}
