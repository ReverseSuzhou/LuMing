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

import com.example.one.Adapter.HomeAdapter;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    //声明控件
    private ImageButton mBtn_back;
    private SwipeRefreshLayout swipe_message;
    private RecyclerView rv_message;
    private TextView error_message;
    private List<String> data = Arrays.asList(new String[]{"哈哈哈哈哈哈", "呵呵呵呵呵呵", "哦哦哦哦哦哦哦哦哦"});
    private HomeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_page);

        //控件部分
        mBtn_back = findViewById(R.id.message_page_1_button_back);
        swipe_message = findViewById(R.id.swipe_message);
        rv_message = findViewById(R.id.rv_message);
        error_message = findViewById(R.id.error_message);
        Refresh();
        swipe_message.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_message.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
        //返回
        mBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MessageActivity.this,HomePage.class);
                startActivity(intent);
            }
        });
    }
    private void Refresh() {

        swipe_message.setRefreshing(false);
        if(data.size()>0){
            swipe_message.setRefreshing(false);
            swipe_message.setVisibility(View.VISIBLE);
            adapter = new HomeAdapter(MessageActivity.this,data);
            rv_message.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
            rv_message.setAdapter(adapter);
        }
        else {
            error_message.setVisibility(View.VISIBLE);
        }

    }
}