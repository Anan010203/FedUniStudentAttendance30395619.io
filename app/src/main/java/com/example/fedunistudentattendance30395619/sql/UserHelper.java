package com.example.fedunistudentattendance30395619.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserHelper extends SQLiteOpenHelper {
    public UserHelper(Context context) {
        super(context, "users.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table user (id integer primary key autoincrement, name text,password text)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
