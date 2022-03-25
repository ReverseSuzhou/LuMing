package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PersonalActivity extends AppCompatActivity {
    //声明控件
    //底下五个
    private ImageButton mBtn_home;
    private ImageButton mBtn_association;
    private ImageButton mBtn_editor;
    private ImageButton mBtn_message;
    private ImageButton mBtn_personal;
    //上面几个
    private ImageButton mBtn_history;
    private ImageButton mBtn_collect;
    private ImageButton mBtn_replace_phone;
    private Button mBtn_modify;
    private Button mBtn_apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_page);

        mBtn_history = findViewById(R.id.personal_page_2_button_history);
        mBtn_collect = findViewById(R.id.personal_page_2_button_collect);
        mBtn_replace_phone = findViewById(R.id.personal_page_2_button_phone);
        mBtn_modify = findViewById(R.id.personal_page_button_exinfo);
        mBtn_apply = findViewById(R.id.personal_page_button_association_apply);

        //历史记录
        mBtn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,HistoryRecordActivity.class);
                startActivity(intent);
            }
        });
        //收藏
        mBtn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,CollectActivity.class);
                startActivity(intent);
            }
        });
        //修改手机号
        mBtn_replace_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,ReplacePhoneActivity.class);
                startActivity(intent);
            }
        });
        //修改个人信息
        mBtn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,ModifyPersonalActivity.class);
                startActivity(intent);
            }
        });
        //申请社团
        mBtn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,AssociationApplyActivity.class);
                startActivity(intent);
            }
        });




//↓↓↓↓↓↓↓↓底下五个按钮的跳转功能↓↓↓↓↓↓↓↓
//使用的时候要修改 ：1.别忘了上面的声明控件部分；2.控件部分中id后面修改成对应的id；3.OnClick中的.this前面的改成当前文件名

        //控件部分
        mBtn_home = findViewById(R.id.personal_page_5_button_main_page);
        mBtn_association = findViewById(R.id.personal_page_5_button_associations_page);
        mBtn_editor = findViewById(R.id.personal_page_5_button_editor_page);
        mBtn_message = findViewById(R.id.personal_page_5_button_message_page);
        mBtn_personal = findViewById(R.id.personal_page_5_button_personal_page);

        //主页面
        mBtn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,HomePage.class);
                startActivity(intent);
            }
        });
        //社团圈
        mBtn_association.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,AssociationActivity.class);
                startActivity(intent);
            }
        });
        //发帖子
        mBtn_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,EditorActivity.class);
                startActivity(intent);
            }
        });
        //消息
        mBtn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });
        //个人信息
        mBtn_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,PersonalActivity.class);
                startActivity(intent);
            }
        });

//↑↑↑↑↑↑↑↑底下五个按钮的跳转功能↑↑↑↑↑↑↑↑
    }
}