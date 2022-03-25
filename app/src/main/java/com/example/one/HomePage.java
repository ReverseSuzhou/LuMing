package com.example.one;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
    private List<String> data;
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

        swipe_home.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Refresh();
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

//    private void Refresh() {
//
//                swipe_home.setRefreshing(false);
//                    data = list;
//                    if(data.size()>0){
//                        swipe_hometab.setVisibility(View.VISIBLE);
//                        homeTabAdapter = new HomeTabAdapter(getActivity(),data);
//                        rv_hometab.setLayoutManager(new LinearLayoutManager(getActivity()));
//                        rv_hometab.setAdapter(homeTabAdapter);
//                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        error_hometab.setVisibility(View.VISIBLE);
//                    }
//
//                }else {
//                    swipe_hometab.setRefreshing(false);
//                    Toast.makeText(getActivity(), "获取数据失败"+e, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//    }
//
}