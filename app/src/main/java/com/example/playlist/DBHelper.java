package com.example.playlist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    final static String DB_NAME = "music.db";
    final static String TABLE_NAME = "goods";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME + "(_id INTEGER, title TEXT, artist TEXT, duration INTEGER, rating REAL)");
        db.execSQL("INSERT INTO "+ TABLE_NAME + " VALUES (1, \"Storm\", \"U2\", 305, 7.9)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}