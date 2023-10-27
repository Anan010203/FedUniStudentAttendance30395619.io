package com.example.fedunistudentattendance30395619.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StuHelper extends SQLiteOpenHelper {
    public StuHelper(Context context) {
        super(context, "student.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table stu (id integer primary key autoincrement, stu_id text,stu_name text," +
                "class_id text,msg text)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
