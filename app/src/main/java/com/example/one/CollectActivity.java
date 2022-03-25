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

import java.util.Arrays;
import java.util.List;

public class CollectActivity extends AppCompatActivity {
    //声明控件
    private ImageButton mBtn_back;
    private SwipeRefreshLayout swipe_collect;
    private RecyclerView rv_collect;
    private TextView error_collect;
    private List<String> data = Arrays.asList(new String[]{"哈哈哈哈哈哈", "呵呵呵呵呵呵", "哦哦哦哦哦哦哦哦哦"});
    private HomeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_page);

        //控件部分
        mBtn_back = findViewById(R.id.collect_page_1_button_back);
        swipe_collect = findViewById(R.id.swipe_collect);
        rv_collect = findViewById(R.id.rv_collect);
        error_collect = findViewById(R.id.error_collect);
        Refresh();
        swipe_collect.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_collect.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                intent = new Intent(CollectActivity.this,PersonalActivity.class);
                startActivity(intent);
            }
        });
    }
    private void Refresh() {

        swipe_collect.setRefreshing(false);
        if(data.size()>0){
            swipe_collect.setRefreshing(false);
            swipe_collect.setVisibility(View.VISIBLE);
            adapter = new HomeAdapter(CollectActivity.this,data);
            rv_collect.setLayoutManager(new LinearLayoutManager(CollectActivity.this));
            rv_collect.setAdapter(adapter);
        }
        else {
            error_collect.setVisibility(View.VISIBLE);
        }

    }
}