package com.example.hp.doubletapapp.database;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SqliteConnector {

    SqliteHelper mDbHelper;

    public SqliteConnector(Application application) {

        mDbHelper = new SqliteHelper(application);
    }
    public void insertWord(String word) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SqliteHelper.WordEntry.COLUMN_NAME_TITLE, word);
        values.put(SqliteHelper.WordEntry.COLUMN_TIMESTAMP, System.currentTimeMillis());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(SqliteHelper.WordEntry.TABLE_NAME, null, values);

    }

    public  void updateWord(WordEntity wordEntity){

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SqliteHelper.WordEntry.COLUMN_NAME_TITLE,wordEntity.getWord()); //These Fields should be your String values of actual column names
        cv.put(SqliteHelper.WordEntry.COLUMN_TIMESTAMP, System.currentTimeMillis());

        db.update(SqliteHelper.WordEntry.TABLE_NAME, cv, "_id="+wordEntity.getId(), null);
    }

    public List<WordEntity> getAllWords(){
        Log.d("SqliteConnector"," calling get All words = "  );
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<WordEntity> wordEntities = new ArrayList<>();
        String[] projection = {
                BaseColumns._ID,
                SqliteHelper.WordEntry.COLUMN_NAME_TITLE
        };

        String selection = "";
        String[] selectionArgs = {  };

        String sortOrder =
                SqliteHelper.WordEntry.COLUMN_TIMESTAMP + " DESC";

        Cursor cursor = db.query(
                SqliteHelper.WordEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        Log.d("SqliteConnector","adding to list = " + cursor.getCount() );
        while(cursor.moveToNext()) {

            WordEntity entity = new WordEntity();
            entity.setWord(cursor.getString(cursor.getColumnIndex(SqliteHelper.WordEntry.COLUMN_NAME_TITLE)));
            entity.setId(cursor.getInt(cursor.getColumnIndex(SqliteHelper.WordEntry._ID)));
            Log.d("SqliteConnector","value from db = " + cursor.getString(cursor.getColumnIndex(SqliteHelper.WordEntry.COLUMN_NAME_TITLE) ));
            wordEntities.add(entity);
        }
        return wordEntities;
    }

}
