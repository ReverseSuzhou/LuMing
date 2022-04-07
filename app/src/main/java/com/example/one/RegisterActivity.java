package com.example.one;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;

public class RegisterActivity extends AppCompatActivity {
    //声明控件
    private Button mBtnAckRegister;
    private Button mBtnVcode;
    private EditText mEtVcode;
    private EditText mEtPhoneNumber;
    private EditText mEtPassword;
    private EditText mEtSurePassword;
    private EditText mEtUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //控件部分
        mBtnAckRegister = findViewById(R.id.btn_ackRegister);
        mBtnVcode = findViewById(R.id.btn_check);
        mEtVcode = findViewById(R.id.re_et_2);
        mEtPhoneNumber = findViewById(R.id.re_et_1);
        mEtPassword = findViewById(R.id.re_et_3);
        mEtSurePassword = findViewById(R.id.re_et_4);
        mEtUserName = findViewById(R.id.re_et_5);

        mBtnVcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEtPhoneNumber.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "未输入手机号", Toast.LENGTH_LONG).show();
                }
                else if (mEtPhoneNumber.getText().toString().length() > 15) {
                    Toast.makeText(getApplicationContext(), "手机号格式不对", Toast.LENGTH_LONG).show();
                }
                else {

                    //发送验证码


                    Toast.makeText(getApplicationContext(), "已发送", Toast.LENGTH_LONG).show();
                }
            }
        });

        //确认注册
        mBtnAckRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (mEtPhoneNumber.getText().toString().equals("") || mEtPassword.getText().toString().equals("") || mEtSurePassword.getText().toString().equals("") || mEtVcode.getText().toString().equals("") || mEtUserName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "信息不全", Toast.LENGTH_LONG).show();
                }
                //else if () {
                //    验证码
                //}
                else if (mEtPassword.getText().toString().length() > 10 || mEtSurePassword.getText().toString().length() > 10 || mEtUserName.getText().toString().length() > 10) {
                    Toast.makeText(getApplicationContext(), "密码或用户名不符合规范", Toast.LENGTH_LONG).show();
                }
                else if (mEtPassword.getText().toString().equals(mEtSurePassword.getText().toString())) {
                    //toast
                    Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_LONG).show();
                    String sql = "insert into user(User_name, U_password, User_phone) values ('" + mEtUserName.getText().toString() + "', '" + mEtPassword.getText().toString() + "', '" + mEtPhoneNumber.getText().toString() + "');";
                    DBUtils dbUtils = new DBUtils();
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                dbUtils.update(sql);
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //如果正确，跳转
                    intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);

                }
                else {
                    //不正确
                    Toast.makeText(getApplicationContext(), "密码错误！", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}