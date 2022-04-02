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
import com.example.one.Bean.Push;

import java.util.Arrays;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    //声明控件
    private ImageButton mBtn_back;
    private ImageButton mBtn_search;
    private SwipeRefreshLayout swipe_result;
    private RecyclerView rv_result;
    private TextView error_result;
    private List<Push> data;
    private HomeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);

        //控件
        mBtn_back = findViewById(R.id.result_page_1_1_imageview_back);
        mBtn_search = findViewById(R.id.result_page_1_2_1_imagebutton_search);
        swipe_result = findViewById(R.id.swipe_result);
        rv_result= findViewById(R.id.rv_result);
        error_result= findViewById(R.id.error_result);
        Refresh();
        swipe_result.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_result.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
    private void Refresh() {

        swipe_result.setRefreshing(false);
        if(data.size()>0){
            swipe_result.setRefreshing(false);
            swipe_result.setVisibility(View.VISIBLE);
            adapter = new HomeAdapter(ResultActivity.this,data);
            rv_result.setLayoutManager(new LinearLayoutManager(ResultActivity.this));
            rv_result.setAdapter(adapter);
        }
        else {
            error_result.setVisibility(View.VISIBLE);
        }

    }
}