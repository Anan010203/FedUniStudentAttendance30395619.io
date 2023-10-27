package com.example.fedunistudentattendance30395619.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClassHelper extends SQLiteOpenHelper {
    public ClassHelper(Context context) {
        super(context, "classes.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table class (id integer primary key autoincrement, title text,teacher text," +
                "start_date date,end_date date,class_time integer)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
