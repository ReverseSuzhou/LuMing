package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.one.util.ToastUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModifyPersonalActivity extends AppCompatActivity {
    EditText user_nameEDT;
    EditText signatureEDT;
    EditText ageEDT;
    Button okBTN;
    RadioGroup radiogroup;

    DBUtils db;
    Thread t;
    ResultSet rs;

    String user_phone;

    String sqlstring;
    String newusername;
    String nowuserphone;
    String oldusername;

    int exit;

    //声明控件
    private ImageButton mBtn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_personal_information_page);
        SaveSharedPreference cat = new SaveSharedPreference();
        oldusername = cat.getUsername();
        nowuserphone = cat.getPhone();

        //控件部分
        mBtn_back = findViewById(R.id.modify_personal_information_page_1_button_back);

        //退出
        mBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(ModifyPersonalActivity.this,PersonalActivity.class);
                startActivity(intent);
            }
        });

        user_nameEDT = findViewById(R.id.modify_personal_information_page_1_edittext_exchange_username);
        signatureEDT = findViewById(R.id.modify_personal_information_page_2_edittext_sign);
        ageEDT = findViewById(R.id.modify_personal_information_page_4_edittext_date);
        okBTN = findViewById(R.id.modify_personal_information_page_linearlayout_5_button_ensure);
        radiogroup = findViewById(R.id.modify_personal_information_page_3_radiogroup_sex);

        okBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newusername = user_nameEDT.getText().toString();

                if(newusername.isEmpty()) {
                    ToastUtil.showMsg(getApplicationContext(), "用户名不能为空");
                }
                else {
                    try {
                        t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                            db = new DBUtils();

                            String sentence = "select * from user where User_phone = '" +nowuserphone +"' and User_name = '" + newusername+"' ;";
                            rs = db.query(sentence);

                            try {
                                if(rs.next()) {
                                    exit = 1;
                                    rs.previous();
                                } else {
                                    exit = 0;
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }



                            if(exit == 1) {
                                ToastUtil.showMsg(getApplication(),"用户名称已存在");
                            }
                            else {
                                String age = ageEDT.getText().toString();
                                String sex;
                                if(radiogroup.getCheckedRadioButtonId() == R.id.modify_personal_information_page_3_radiogroup_sex1) {
                                    sex = "男";
                                }
                                else if(radiogroup.getCheckedRadioButtonId() == R.id.modify_personal_information_page_3_radiogroup_sex2) {
                                    sex = "女";
                                }
                                else {
                                    sex ="";
                                }
                                String update = "update user set User_name = '" + newusername +"' , User_sex = '"+ sex + "' where User_phone = '"+nowuserphone+"' and User_name = '"+ oldusername +"' ;";
                                db.update(update);
                                cat.setUsername(newusername);
                                ToastUtil.showMsg(getApplicationContext(),"成功");

                            }

                            }
                        });
                        t.start();
                        ToastUtil.showMsg(getApplicationContext(),"修改成功");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}