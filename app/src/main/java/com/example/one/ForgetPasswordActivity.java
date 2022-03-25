package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgetPasswordActivity extends AppCompatActivity {
    //声明控件
    private Button mBtnAckForget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        //控件部分
        mBtnAckForget = findViewById(R.id.btn_ackForget);

        //确认修改
        mBtnAckForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent= new Intent(ForgetPasswordActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}