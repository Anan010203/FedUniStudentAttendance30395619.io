package com.example.fedunistudentattendance30395619;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fedunistudentattendance30395619.adapter.StuListAdapter;
import com.example.fedunistudentattendance30395619.bean.Classbean;
import com.example.fedunistudentattendance30395619.bean.StudentBean;
import com.example.fedunistudentattendance30395619.dao.ClassDao;
import com.example.fedunistudentattendance30395619.dao.StuDao;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassActivity extends AppCompatActivity {
    private StuDao stuDao;
    private Spinner spinner;
    private TextView tv_tip;
    private ListView listView;
    public static Classbean classbean;
    private StuListAdapter listAdapter;
    private List<StudentBean> studentBeans = new ArrayList<>();
    List<String> elements = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c");
    private List<String> daysOfWeek = new ArrayList<>(
            Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));
    int count = 0;
    private ClassDao classDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        stuDao = new StuDao(this);
        classDao = new ClassDao(this);

        spinner = findViewById(R.id.spinner);
        tv_tip = findViewById(R.id.tv_tip);
        tv_tip.setText("The class is scheduled every "+daysOfWeek.get(classbean.getClass_time())+".");

        initList(elements.get(count));

        findViewById(R.id.btn_save).setOnClickListener(view -> {
            Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show();
            finish();
        });
        findViewById(R.id.btn_del).setOnClickListener(view -> {
            classDao.delete(classbean.getId());
            Toast.makeText(this, "delete success", Toast.LENGTH_SHORT).show();
            finish();
        });


        findViewById(R.id.btn_add).setOnClickListener(view -> {
            //  Adding students
            final BottomSheetDialog bsd = new BottomSheetDialog(this, R.style.BottomSheetEdit);
            final View v = View.inflate(this, R.layout.dialog_msg, null);
            bsd.setContentView(v);
            bsd.show();

            final TextInputEditText tv_id = v.findViewById(R.id.tv_id);
            final TextInputEditText tv_stu = v.findViewById(R.id.tv_stu);
            final MaterialButton b1 = v.findViewById(R.id.button1);
            final MaterialButton b2 = v.findViewById(R.id.button2);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bsd.dismiss();
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(tv_id.getText().toString()) || TextUtils.isEmpty(tv_stu.getText().toString())) {
                        Toast.makeText(ClassActivity.this, "please input", Toast.LENGTH_SHORT).show();
                    } else {
                        stuDao.insert(new StudentBean(0,tv_id.getText().toString(),tv_stu.getText().toString(),""+classbean.getId(),""));
                        Toast.makeText(ClassActivity.this, "add success", Toast.LENGTH_SHORT).show();

                        initList(elements.get(count));
                        bsd.dismiss();
                    }
                }
            });


        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                count = i;
                initList(elements.get(count));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    private void initList(String state){
        listView = findViewById(R.id.list);
        listAdapter = new StuListAdapter(this,studentBeans,state);
        listView.setAdapter(listAdapter);

        List<StudentBean> sb = stuDao.query(""+classbean.getId(),state);
        if (sb.size()>0){
            studentBeans.clear();
            studentBeans.addAll(sb);
        }
        listAdapter.notifyDataSetChanged();

    }
}