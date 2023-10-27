package com.example.fedunistudentattendance30395619.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.fedunistudentattendance30395619.R;
import com.example.fedunistudentattendance30395619.bean.Classbean;
import com.example.fedunistudentattendance30395619.bean.StudentBean;
import com.example.fedunistudentattendance30395619.dao.StuDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StuSSListAdapter extends BaseAdapter {
    private List<StudentBean> studentBeans;
    private List<Classbean> classbeans;

    private String state = "xxxx-xx-xx";
    private Context context;
    private StuDao stuDao;
    List<String> elements = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c");
    public StuSSListAdapter(Context context, List<StudentBean> studentBeans,List<Classbean> classbeans, String state) {
        this.studentBeans = studentBeans;
        this.classbeans = classbeans;
        this.context = context;
        this.state =state;
    }

    @Override
    public int getCount() {
        return studentBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return studentBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.stu_ss_item,null);

        TextView tv_class = view.findViewById(R.id.tv_class);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_isattend =view.findViewById(R.id.tv_isattend);

        //Determine class names
        String class_name="";
        for (int i = 0; i < classbeans.size(); i++) {
            if ((""+classbeans.get(i).getId()).equals(studentBeans.get(position).getClass_id())){
                class_name = classbeans.get(i).getTitle();
            }
        }
        tv_class.setText(class_name);
        tv_name.setText(studentBeans.get(position).getStu_name());

        for (int i = 0; i < classbeans.size(); i++) {
            int week = getWeeksBetween(classbeans.get(i).getStart_date(),state);
            if (week>=12){
                tv_isattend.setText(R.string.noattend);
                continue;
            }
            if (studentBeans.get(position).getMsg().contains(elements.get(week))){

                tv_isattend.setText(R.string.attend);
            }else {
                tv_isattend.setText(R.string.noattend);
            }

        }


        return view;
    }
    //According to the date, it is the number of weeks between the two dates
    public static int getWeeksBetween(String dateString1, String dateString2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date date1 = sdf.parse(dateString1);
            Date date2 = sdf.parse(dateString2);

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date1);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date2);

            long diffInMillis = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
            int daysDiff = (int) (diffInMillis / (24 * 60 * 60 * 1000));

            return daysDiff / 7;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1; // Return an error code or throw an exception, depending on the situation
        }
    }
}
