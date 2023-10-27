package com.example.fedunistudentattendance30395619;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.fedunistudentattendance30395619.bean.Userbean;
import com.example.fedunistudentattendance30395619.sql.UserHelper;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText account,password;
    private Button btn_zhuce,btn_denglu;
    private CheckBox checkBox;
    private UserHelper userHelper;
    private CheckBox checkBox3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userHelper = new UserHelper(this);
        account = findViewById(R.id.account);
        checkBox = findViewById(R.id.checkBox);
        checkBox3 = findViewById(R.id.checkBox3);
        password =  findViewById(R.id.password);
        btn_zhuce =  findViewById(R.id.btn_zhuce);
        btn_denglu = findViewById(R.id.btn_denglu);


        btn_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    password.setInputType(0x90);
                }else {
                    password.setInputType(0x81);
                }

            }
        });

        btn_denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String z = account.getText().toString().trim();
                String m = password.getText().toString().trim();
                if (z.isEmpty()||m.isEmpty()){
                    Toast.makeText(LoginActivity.this, "please input", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Decide whether to agree to the agreement
                if (checkBox.isChecked() == true) {
                    String mm = "";//The password to save the data
                    SQLiteDatabase db = userHelper.getReadableDatabase();
                    List<Userbean> userbeans = new ArrayList<Userbean>();
                    //Query the user from the database
                    String query_sql = "select * from user";
                    Cursor cursor = db.rawQuery(query_sql, null);

                    if (0 != cursor.getCount()) {
                        cursor.moveToFirst();
                        do {
                            int id = cursor.getInt(0);
                            String a = cursor.getString(1);
                            String b = cursor.getString(2);
                            Userbean userbean = new Userbean(id, a, b);
                            userbeans.add(userbean);
                        }while (cursor.moveToNext());
                        //Find the same account
                        for (int i = 0; i < userbeans.size(); i++) {
                            if(userbeans.get(i).getName().equals(z)){
                                mm = userbeans.get(i).getPassword();
                            }
                        }
                    }
                    if (mm.isEmpty()){
                        Toast.makeText(LoginActivity.this, "account not reg", Toast.LENGTH_SHORT).show();
                    }else {
                        if (mm.equals(m)){
                            Toast.makeText(LoginActivity.this, "login success！！", Toast.LENGTH_SHORT).show();
                            MainActivity.userbean = new Userbean(0,z,m);
                            //  Jump
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, "password is error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(LoginActivity.this, R.string.please, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}