package com.example.hp.doubletapapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static com.example.hp.doubletapapp.database.SqliteHelper.WordEntry.COLUMN_TIMESTAMP;

public class SqliteHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "word_list.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + WordEntry.TABLE_NAME + " (" +
                    WordEntry._ID + " INTEGER PRIMARY KEY," +
                    WordEntry.COLUMN_NAME_TITLE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP" + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WordEntry.TABLE_NAME;

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static class WordEntry implements BaseColumns {
        public static final String TABLE_NAME = "word_list";
        public static final String COLUMN_NAME_TITLE = "word";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }


}
