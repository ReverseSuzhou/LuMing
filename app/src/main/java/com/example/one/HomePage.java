package com.example.one;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;



import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.one.Adapter.HomeAdapter;

import java.util.Arrays;
import java.util.List;

public class HomePage extends AppCompatActivity {
    //声明控件
    private ImageButton mBtn_home;
    private ImageButton mBtn_association;
    private ImageButton mBtn_editor;
    private ImageButton mBtn_message;
    private ImageButton mBtn_personal;
    private ImageButton mBtn_search;
    private SwipeRefreshLayout swipe_home;
    private RecyclerView rv_home;
    private TextView error_home;
    private List<String> data = Arrays.asList(new String[]{"哈哈哈哈哈哈", "呵呵呵呵呵呵", "哦哦哦哦哦哦哦哦哦"});
    private HomeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        //搜索
        mBtn_search = findViewById(R.id.main_page_1_1_button_search);
        mBtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(HomePage.this,ResultActivity.class);
                startActivity(intent);
            }
        });



//↓↓↓↓↓↓↓↓底下五个按钮的跳转功能↓↓↓↓↓↓↓↓
//使用的时候要修改 ：1.别忘了上面的声明控件部分；2.控件部分中id后面修改成对应的id；3.OnClick中的.this前面的改成当前文件名

        //控件部分
        mBtn_home = findViewById(R.id.main_page_4_button_main_page);
        mBtn_association = findViewById(R.id.main_page_4_button_associations_page);
        mBtn_editor = findViewById(R.id.main_page_4_button_editor_page);
        mBtn_message = findViewById(R.id.main_page_4_button_message_page);
        mBtn_personal = findViewById(R.id.main_page_4_button_personal_page);
        swipe_home = findViewById(R.id.swipe_home);
        rv_home = findViewById(R.id.rv_home);
        error_home = findViewById(R.id.error_home);
        Refresh();
        swipe_home.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });

        //主页面
        mBtn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(HomePage.this, HomePage.class);
                startActivity(intent);
            }
        });
        //社团圈
        mBtn_association.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(HomePage.this,AssociationActivity.class);
                startActivity(intent);
            }
        });
        //发帖子
        mBtn_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(HomePage.this,EditorActivity.class);
                startActivity(intent);
            }
        });
        //消息
        mBtn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(HomePage.this, MessageActivity.class);
                startActivity(intent);
            }
        });
        //个人信息
        mBtn_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(HomePage.this,PersonalActivity.class);
                startActivity(intent);
            }
        });

//↑↑↑↑↑↑↑↑底下五个按钮的跳转功能↑↑↑↑↑↑↑↑


    }

    private void Refresh() {

        swipe_home.setRefreshing(false);
        if(data.size()>0){
            swipe_home.setRefreshing(false);
            swipe_home.setVisibility(View.VISIBLE);
            adapter = new HomeAdapter(HomePage.this,data);
            rv_home.setLayoutManager(new LinearLayoutManager(HomePage.this));
            rv_home.setAdapter(adapter);
        }
        else {
            error_home.setVisibility(View.VISIBLE);
        }

    }

}