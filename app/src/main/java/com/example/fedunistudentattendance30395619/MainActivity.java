package com.example.fedunistudentattendance30395619;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fedunistudentattendance30395619.adapter.ClassListAdapter;
import com.example.fedunistudentattendance30395619.bean.Classbean;
import com.example.fedunistudentattendance30395619.bean.Userbean;
import com.example.fedunistudentattendance30395619.dao.ClassDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Userbean userbean;
    private ListView listView;
    private ClassListAdapter adapter;
    private List<Classbean> classbeans = new ArrayList<>();
    private ClassDao classDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        classDao = new ClassDao(this);


        listView = findViewById(R.id.listview);
        adapter = new ClassListAdapter(this,classbeans);
        listView.setAdapter(adapter);
        List<Classbean> cb = classDao.query(MainActivity.userbean.getName());
        if (cb.size()>0){
            classbeans.clear();
            classbeans.addAll(cb);
        }
        adapter.notifyDataSetChanged();



        findViewById(R.id.imageView).setOnClickListener(view -> {
            //  add
            Intent intent = new Intent(this,AddActivity.class);
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            final androidx.appcompat.app.AlertDialog mDialog = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                    .setPositiveButton("confirm",null)
                    .setNegativeButton("cancel",null)
                    .create();
            mDialog.setTitle(classbeans.get(i).getTitle());
            final View contentView = getLayoutInflater().inflate(R.layout.dialog_edit,null);
            mDialog.setView(contentView);
            final TextView textInputLayout = contentView.findViewById(R.id.textView);
            textInputLayout.setTextColor(Color.RED);
            textInputLayout.setText("Whether to delete the course");

            mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button positiveButton = mDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
                    Button negativeButton = mDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            classDao.delete(classbeans.get(i).getId());
                            classbeans.remove(i);
                            mDialog.dismiss();
                            adapter.notifyDataSetChanged();
                        }
                    });
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });
                }
            });
            mDialog.show();
            WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
            layoutParams.width = getResources().getDisplayMetrics().widthPixels/5*4;
            mDialog.getWindow().setAttributes(layoutParams);


            return true;
        });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            ClassActivity.classbean = classbeans.get(i);
            Intent intent = new Intent(MainActivity.this,ClassActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(MainActivity.this,LookActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Classbean> cb = classDao.query(MainActivity.userbean.getName());
        if (cb.size()>=0){
            classbeans.clear();
            classbeans.addAll(cb);
        }
        adapter.notifyDataSetChanged();
    }
}