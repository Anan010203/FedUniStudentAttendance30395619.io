package com.example.fedunistudentattendance30395619.bean;

public class StudentBean {
    private int id;
    private String stu_id;
    private String stu_name;
    private String class_id;

    private String msg;//Status of attendance1，2，3，4，5，6，7，8，9，a,b,c

    public StudentBean(int id, String stu_id, String stu_name, String class_id, String msg) {
        this.id = id;
        this.stu_id = stu_id;
        this.stu_name = stu_name;
        this.class_id = class_id;
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
