package com.example.fedunistudentattendance30395619.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fedunistudentattendance30395619.bean.Classbean;
import com.example.fedunistudentattendance30395619.sql.ClassHelper;

import java.util.ArrayList;
import java.util.List;


public class ClassDao {
    private ClassHelper mHelper;

    public ClassDao(Context context) {
        mHelper = new ClassHelper(context);
    }

    public void insert(Classbean classbean) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String insert_sql = "INSERT INTO class (title,teacher,start_date,end_date,class_time) VALUES (?,?,?,?,?)";
        Object[] obj = {classbean.getTitle(),classbean.getTeacher(),classbean.getStart_date(),classbean.getEnd_date(),classbean.getClass_time()};
        db.execSQL(insert_sql, obj);
        db.close();
    }
    public List<Classbean> query(String teacher) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        List<Classbean> classbeans = new ArrayList<>();
        String query_sql = "select * from class";
        Cursor cursor = db.rawQuery(query_sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String a = cursor.getString(1);
                String b = cursor.getString(2);
                String c = cursor.getString(3);
                String d = cursor.getString(4);
                int e = cursor.getInt(5);
                if (!b.equals(teacher)){
                    continue;
                }
                Classbean classbean = new Classbean(id, a, b, c, d, e);
                classbeans.add(classbean);
            } while (cursor.moveToNext());
        }
        return classbeans;
    }


    public boolean delete(int id) {
        try {
            SQLiteDatabase db = mHelper.getReadableDatabase();
            db.delete("class", "id = ?", new String[] { ""+id });
            return true;
        }catch (Exception e) {
            return false;
        }

    }

}
