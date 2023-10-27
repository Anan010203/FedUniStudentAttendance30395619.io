package com.example.fedunistudentattendance30395619;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fedunistudentattendance30395619.bean.Classbean;
import com.example.fedunistudentattendance30395619.dao.ClassDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    private ClassDao classDao;
    private DatePicker datePicker;
    private int year,month,day;
    private Date now ,date;
    private TextView tv_day;
    private ImageView iv_add,iv_unadd;
    private EditText et_title;
    private int count=1;
    private List<String> daysOfWeek = new ArrayList<>(
            Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        classDao = new ClassDao(this);

        datePicker = findViewById(R.id.datepicker);
        et_title = findViewById(R.id.et_title);
        tv_day = findViewById(R.id.textView8);
        iv_add = findViewById(R.id.add);
        iv_unadd = findViewById(R.id.unadd);

        now  = new Date();
        Calendar calendar=Calendar.getInstance();//Gets the calendar for the current time, otherwise the default is 1900


        year=calendar.get(Calendar.YEAR);//This year
        month=calendar.get(Calendar.MONTH);//This month
        day=calendar.get(Calendar.DAY_OF_MONTH);//Today

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            //onDateChangedThe argument is the selected date
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                date = new Date(i-1900,i1,i2);
                year=i-1900;
                month=i1;
                day=i2;
            }
        });
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count>6){
                    count=6;
                }
                tv_day.setText(daysOfWeek.get(count));
            }
        });
        iv_unadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                if(count < 0){
                    count = 0;
                }
                tv_day.setText(daysOfWeek.get(count));
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2023/10/25 add
                String title = et_title.getText().toString().trim();
                if (title.isEmpty()){
                    Toast.makeText(AddActivity.this, "input title", Toast.LENGTH_SHORT).show();
                    return;
                }
                classDao.insert(new Classbean(0,title, MainActivity.userbean.getName(),dateToString(date),dateToString(getDateAfter12Weeks(date)),count));
                Toast.makeText(AddActivity.this, "add success", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
    public static String dateToString(Date date) {
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");//Date Format
        String s = sformat.format(date);

        return s;
    }
    public static Date getDateAfter12Weeks(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, 12);
        return calendar.getTime();
    }
}