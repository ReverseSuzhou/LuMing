package com.example.one;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {
    //声明控件
    private Button mBtnAckRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //控件部分
        mBtnAckRegister = findViewById(R.id.btn_ackRegister);
        //确认注册
        mBtnAckRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent= new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}