package com.example.one;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.wenqu.util.ToastUtil;

public class MainActivity extends AppCompatActivity {

    //声明控件
    private Button mBtnRegister;
    private Button mBtnForget;
    private Button mBtnLogin;
    private EditText mEtUser;
    private EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.login);

//        init();
//        initEvent();
//        MobSDK.init(this);


        //这是最底下的那个跑马灯的特效
        TextView huizhi = findViewById(R.id.under);
        huizhi.setSelected(true);

        //控件部分
        mEtUser = findViewById(R.id.etUser);
        mEtPassword = findViewById(R.id.etPassword);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnRegister = findViewById(R.id.btn_register);
        mBtnForget = findViewById(R.id.btn_forget);


        //注册
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //忘记密码
        mBtnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });



        //点击登录
        mBtnLogin.setOnClickListener(this::onClick);
    }


//    //请求验证码，county表示国家，如86；phone表示手机号
//    SMMSSDK.re




    //点击登录
    public void onClick (View v){
        //需要获取获得的用户名和密码
        String username = mEtUser.getText().toString();
        String password = mEtPassword.getText().toString();
        String ok = "登录成功！";
        String fail = "登录失败！";

        Intent intent = null;

        if (username.equals("admin") && password.equals("123456")){
            //toast
            Toast.makeText(getApplicationContext(), ok, Toast.LENGTH_LONG).show();

            //如果正确，跳转
            intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);

        }
        else{
            //不正确
            ToastUtil.showMsg(MainActivity.this, fail);
//            //登录失败居中显示
//            Toast toastcenter = Toast.makeText(getApplicationContext(), fail, Toast.LENGTH_LONG);
//            toastcenter.setGravity(Gravity.CENTER, 0, 0);
//            toastcenter.show();
        }
    }

}