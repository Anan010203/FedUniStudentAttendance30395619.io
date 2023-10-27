package com.example.fedunistudentattendance30395619;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fedunistudentattendance30395619.adapter.StuSSListAdapter;
import com.example.fedunistudentattendance30395619.bean.Classbean;
import com.example.fedunistudentattendance30395619.bean.StudentBean;
import com.example.fedunistudentattendance30395619.dao.ClassDao;
import com.example.fedunistudentattendance30395619.dao.StuDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LookActivity extends AppCompatActivity {
    private Button dateButton;
    private StuDao stuDao;
    private ClassDao classDao;
    private List<StudentBean> studentBeans_all = new ArrayList<>();
    private List<StudentBean> studentBeans = new ArrayList<>();
    private List<Classbean> classbeans = new ArrayList<>();
    private ListView listView;
    private StuSSListAdapter adapter;
    String selectedDate;
    int weekday = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);
        stuDao = new StuDao(this);
        classDao = new ClassDao(this);







        dateButton = findViewById(R.id.dateButton);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }
    private void initList(){
        listView = findViewById(R.id.list);
        studentBeans.clear();
        List<StudentBean> sb = stuDao.query();
        if (sb.size()>0){
            studentBeans_all.clear();
            studentBeans_all.addAll(sb);
        }
        List<Classbean> cb = classDao.query(MainActivity.userbean.getName());
        if (cb.size()>0){
            classbeans.clear();
            classbeans.addAll(cb);
        }
        //删除无关课程
        for (int i = classbeans.size()-1; i >=0 ; i--) {
            if (weekday == -1){
                Toast.makeText(this, "please input real date", Toast.LENGTH_SHORT).show();
                return;
            }

            if (classbeans.get(i).getClass_time() != weekday ){
                classbeans.remove(i);
                continue;
            }
            if (!isDateBetween(classbeans.get(i).getStart_date(),classbeans.get(i).getEnd_date(),selectedDate)){
                classbeans.remove(i);
            }
        }

        //添加相关学生
        for (int i = studentBeans_all.size()-1; i >=0 ; i--) {
            for (int j = 0; j < classbeans.size(); j++) {
                if (studentBeans_all.get(i).getClass_id().equals(""+classbeans.get(j).getId())){
                    studentBeans.add(studentBeans_all.get(i));
                }
            }
        }

        adapter = new StuSSListAdapter(this, studentBeans, classbeans,selectedDate);
        listView.setAdapter(adapter);

        if (studentBeans.size()==0){
            Toast.makeText(this, "no things", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();

    }
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    selectedDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth1;
                    dateButton.setText(selectedDate);
                    weekday = getDayOfWeek(selectedDate);

                    initList();

                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }


    //According to the date, it is the day of the week, Sunday 0, Monday 1……
    public static int getDayOfWeek(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = sdf.parse(dateString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            // 将Calendar的星期日定义为0，星期一定义为1，以此类推
            return dayOfWeek-1;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
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
            return -1; // 返回一个错误代码或者抛出异常，具体要根据实际情况来处理
        }
    }

    public static boolean isDateBetween(String dateString1, String dateString2, String dateString3) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date date1 = sdf.parse(dateString1);
            Date date2 = sdf.parse(dateString2);
            Date date3 = sdf.parse(dateString3);

            return (date3.equals(date1) || (date3.after(date1) && date3.before(date2)));
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Return an error code or throw an exception, depending on the situation
        }
    }
}