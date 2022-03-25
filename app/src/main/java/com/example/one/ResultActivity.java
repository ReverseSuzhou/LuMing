package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ResultActivity extends AppCompatActivity {
    //声明控件
    private ImageButton mBtn_back;
    private ImageButton mBtn_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);

        //控件
        mBtn_back = findViewById(R.id.result_page_1_1_imageview_back);
        mBtn_search = findViewById(R.id.result_page_1_2_1_imagebutton_search);

        //返回
        mBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(ResultActivity.this,HomePage.class);
                startActivity(intent);
            }
        });
        //搜索
        mBtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(ResultActivity.this,ResultActivity.class);
                startActivity(intent);
            }
        });
    }
}