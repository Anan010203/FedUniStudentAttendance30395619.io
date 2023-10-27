package com.example.fedunistudentattendance30395619.bean;


//Curriculum
public class Classbean {
    private int id;
    private String title;
    private String teacher;

    private String start_date;

    private String end_date;

    private int class_time;


    public Classbean(int id, String title, String teacher, String start_date, String end_date, int class_time) {
        this.id = id;
        this.title = title;
        this.teacher = teacher;
        this.start_date = start_date;
        this.end_date = end_date;
        this.class_time = class_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getClass_time() {
        return class_time;
    }

    public void setClass_time(int class_time) {
        this.class_time = class_time;
    }
}
