package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AssociationApplyActivity extends AppCompatActivity {
    //声明控件
    private ImageButton mBtn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.association_apply_page);

        //控件部分
        mBtn_back = findViewById(R.id.association_apply_page_1_button_back);

        //返回
        mBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(AssociationApplyActivity.this,PersonalActivity.class);
                startActivity(intent);
            }
        });
    }
}