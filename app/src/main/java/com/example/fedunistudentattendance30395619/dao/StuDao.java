package com.example.fedunistudentattendance30395619.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fedunistudentattendance30395619.bean.Classbean;
import com.example.fedunistudentattendance30395619.bean.StudentBean;
import com.example.fedunistudentattendance30395619.sql.ClassHelper;
import com.example.fedunistudentattendance30395619.sql.StuHelper;

import java.util.ArrayList;
import java.util.List;


public class StuDao {
    private StuHelper mHelper;

    public StuDao(Context context) {
        mHelper = new StuHelper(context);
    }

    public void insert(StudentBean studentBean) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String insert_sql = "INSERT INTO stu (stu_id,stu_name,class_id,msg) VALUES (?,?,?,?)";
        Object[] obj = {studentBean.getStu_id(),studentBean.getStu_name(),studentBean.getClass_id(),studentBean.getMsg()};
        db.execSQL(insert_sql, obj);
        db.close();
    }
    public List<StudentBean> query(String class_id,String msg) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        List<StudentBean> studentBeans = new ArrayList<>();
        String query_sql = "select * from stu";
        Cursor cursor = db.rawQuery(query_sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String a = cursor.getString(1);
                String b = cursor.getString(2);
                String c = cursor.getString(3);
                String d = cursor.getString(4);
                if (c.equals(class_id)){
                    StudentBean studentBean = new StudentBean(id, a, b, c, d);
                    studentBeans.add(studentBean);
                }
            } while (cursor.moveToNext());
        }
        return studentBeans;
    }
    public List<StudentBean> query() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        List<StudentBean> studentBeans = new ArrayList<>();
        String query_sql = "select * from stu";
        Cursor cursor = db.rawQuery(query_sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String a = cursor.getString(1);
                String b = cursor.getString(2);
                String c = cursor.getString(3);
                String d = cursor.getString(4);
                StudentBean studentBean = new StudentBean(id, a, b, c, d);
                studentBeans.add(studentBean);
            } while (cursor.moveToNext());
        }
        return studentBeans;
    }


    public boolean delete(int id) {
        try {
            SQLiteDatabase db = mHelper.getReadableDatabase();
            db.delete("stu", "id = ?", new String[] { ""+id });
            return true;
        }catch (Exception e) {
            return false;
        }

    }

    // Update class records

    public boolean update(StudentBean studentBean){
        try {
            SQLiteDatabase db = mHelper.getReadableDatabase();
            String[] whereArgs = { String.valueOf(studentBean.getId()) };
            ContentValues cv = new ContentValues();
            cv.put("msg", studentBean.getMsg());
            db.update("stu",cv,"id=?",whereArgs);
            return true;
        }catch (Exception e) {
            return false;
        }
    }



}
