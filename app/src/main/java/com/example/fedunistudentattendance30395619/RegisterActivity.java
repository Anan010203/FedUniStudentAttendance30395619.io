package com.example.fedunistudentattendance30395619;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.fedunistudentattendance30395619.bean.Userbean;
import com.example.fedunistudentattendance30395619.sql.UserHelper;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private Button btn_zhuce,btn_back;
    private EditText account,password1,password2;
    private UserHelper userHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        userHelper = new UserHelper(this);

        btn_zhuce = findViewById(R.id.btn_zhuce);
        btn_back = findViewById(R.id.btn_back);
        account = findViewById(R.id.account);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);

        btn_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String z = account.getText().toString().trim();
                String m1 = password1.getText().toString().trim();
                String m2 = password2.getText().toString().trim();
                if (z.isEmpty()||m1.isEmpty()||m2.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "please input", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!m1.equals(m2)){
                    Toast.makeText(RegisterActivity.this, "password is ", Toast.LENGTH_SHORT).show();
                    return;
                }
                String mm = "";
                SQLiteDatabase db = userHelper.getReadableDatabase();
                List<Userbean> userbeans = new ArrayList<Userbean>();
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
                    for (int i = 0; i < userbeans.size(); i++) {
                        if(userbeans.get(i).getName().equals(z)){
                            mm = userbeans.get(i).getPassword();
                        }
                    }
                }

                if (!mm.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Account has been registered", Toast.LENGTH_SHORT).show();
                    return;
                }

                String insert_sql = "INSERT INTO user (name,password) VALUES (?,?)";
                Object[] obj = {z, m1};
                db.execSQL(insert_sql, obj);
                db.close();
                Toast.makeText(RegisterActivity.this, "registered successfully！！", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}