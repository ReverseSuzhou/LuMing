package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordActivity extends AppCompatActivity {
    //声明控件
    private Button mBtnAckForget;
    private Button mBtnVcode;
    private EditText mEtVcode;
    private EditText mEtPhoneNumber;
    private EditText mEtPassword;
    private EditText mEtSurePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        //控件部分
        mBtnAckForget = findViewById(R.id.btn_ackForget);
        mBtnVcode = findViewById(R.id.btn_check);
        mEtVcode = findViewById(R.id.re_et_2);
        mEtPhoneNumber = findViewById(R.id.re_et_1);
        mEtPassword = findViewById(R.id.re_et_3);
        mEtSurePassword = findViewById(R.id.re_et_4);

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

        mBtnAckForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (mEtPhoneNumber.getText().toString().equals("") || mEtPassword.getText().toString().equals("") || mEtSurePassword.getText().toString().equals("") || mEtVcode.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "信息不全", Toast.LENGTH_LONG).show();
                }
                //else if () {
                //    验证码
                //}
                else if (mEtPassword.getText().toString().length() > 10 || mEtSurePassword.getText().toString().length() > 10) {
                    Toast.makeText(getApplicationContext(), "密码不符合规范", Toast.LENGTH_LONG).show();
                }
                else if (mEtPassword.getText().toString().equals(mEtSurePassword.getText().toString())) {
                    //toast
                    Toast.makeText(getApplicationContext(), "修改成功！", Toast.LENGTH_LONG).show();
                    String sql = "update user set U_password = '" + mEtSurePassword.getText().toString() + "' where User_phone = '" + mEtPhoneNumber.getText().toString() + "';";
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
                    intent = new Intent(ForgetPasswordActivity.this, MainActivity.class);
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