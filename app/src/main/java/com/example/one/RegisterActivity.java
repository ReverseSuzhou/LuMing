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
    private EditText mEtPhoneNumber;
    private EditText mEtPassword;
    private EditText mEtSurePassword;

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //控件部分
        mBtnAckRegister = findViewById(R.id.btn_ackRegister);
        mEtPhoneNumber = findViewById(R.id.re_et_1);
        mEtPassword = findViewById(R.id.re_et_3);
        mEtSurePassword = findViewById(R.id.re_et_4);
        //确认注册
        mBtnAckRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ok = "注册成功！";
                String fail = "注册失败！";

                Intent intent = null;
                if (mEtPhoneNumber.getText().toString().equals("") || mEtPassword.getText().toString().equals("") || mEtSurePassword.getText().toString().equals("")) {
                    com.example.one.util.ToastUtil.showMsg(RegisterActivity.this, "信息不全");
                }
                else if (mEtPassword.getText().toString().equals(mEtSurePassword.getText().toString())){
                    //toast
                    Toast.makeText(getApplicationContext(), ok, Toast.LENGTH_LONG).show();
                    String sql = "insert into user(User_name, U_password, User_phone) values ('无名之辈', '" + mEtPassword.getText().toString() + "', '" + mEtPhoneNumber.getText().toString() + "');";
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
                else{
                    //不正确
                    com.example.one.util.ToastUtil.showMsg(RegisterActivity.this, fail);
                }

            }
        });
    }
}