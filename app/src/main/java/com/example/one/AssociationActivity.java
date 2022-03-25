package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AssociationActivity extends AppCompatActivity {
    //声明控件
    private ImageButton mBtn_home;
    private ImageButton mBtn_association;
    private ImageButton mBtn_editor;
    private ImageButton mBtn_message;
    private ImageButton mBtn_personal;
    private ImageButton mBtn_search;
    private Button mBtn_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.association_page);

        //搜索
        mBtn_search = findViewById(R.id.association_page_1_button_search);
        mBtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(AssociationActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        //搜索框
        mBtn_text = findViewById(R.id.main_page_1_edittext_search);
        mBtn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(AssociationActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

//↓↓↓↓↓↓↓↓底下五个按钮的跳转功能↓↓↓↓↓↓↓↓
//使用的时候要修改 ：1.别忘了上面的声明控件部分；2.控件部分中id后面修改成对应的id；3.OnClick中的.this前面的改成当前文件名

        //控件部分
        mBtn_home = findViewById(R.id.association_page_linearlayout_3_button_main_page);
        mBtn_association = findViewById(R.id.association_page_linearlayout_3_button_associations_page);
        mBtn_editor = findViewById(R.id.association_page_linearlayout_3_button_editor_page);
        mBtn_message = findViewById(R.id.association_page_linearlayout_3_button_message_page);
        mBtn_personal = findViewById(R.id.association_page_linearlayout_3_button_personal_page);

        //主页面
        mBtn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(AssociationActivity.this,HomePage.class);
                startActivity(intent);
            }
        });
        //社团圈
        mBtn_association.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(AssociationActivity.this,AssociationActivity.class);
                startActivity(intent);
            }
        });
        //发帖子
        mBtn_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(AssociationActivity.this,EditorActivity.class);
                startActivity(intent);
            }
        });
        //消息
        mBtn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(AssociationActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });
        //个人信息
        mBtn_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(AssociationActivity.this,PersonalActivity.class);
                startActivity(intent);
            }
        });

//↑↑↑↑↑↑↑↑底下五个按钮的跳转功能↑↑↑↑↑↑↑↑
    }
}