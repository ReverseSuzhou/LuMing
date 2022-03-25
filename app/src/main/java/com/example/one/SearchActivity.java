package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SearchActivity extends AppCompatActivity {
    //声明控件
    private ImageButton mBtn_back;
    private ImageButton mBtn_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        //控件
        mBtn_back = findViewById(R.id.search_page_1_2_button_cancel);
        mBtn_search = findViewById(R.id.search_page_1_1_1_imagebutton_search);

        //返回
        mBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(SearchActivity.this,HomePage.class);
                startActivity(intent);
            }
        });
        //搜索
        mBtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(SearchActivity.this,ResultActivity.class);
                startActivity(intent);
            }
        });
    }
}