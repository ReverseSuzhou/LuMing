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

public class HistoryRecordActivity extends AppCompatActivity {
    //声明控件
    private ImageButton mBtn_back;
    private SwipeRefreshLayout swipe_his_rec;
    private RecyclerView rv_his_rec;
    private TextView error_his_rec;
    private List<Push> data;
    private HomeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_record_page);

        //控件部分
        mBtn_back = findViewById(R.id.history_record_page_1_1_button_back);
        swipe_his_rec = findViewById(R.id.swipe_his_rec);
        rv_his_rec = findViewById(R.id.rv_his_rec);
        error_his_rec = findViewById(R.id.error_his_rec);
        Refresh();
        swipe_his_rec.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_his_rec.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                intent = new Intent(HistoryRecordActivity.this,PersonalActivity.class);
                startActivity(intent);
            }
        });
    }
    private void Refresh() {

        swipe_his_rec.setRefreshing(false);
        if(data.size()>0){
            swipe_his_rec.setRefreshing(false);
            swipe_his_rec.setVisibility(View.VISIBLE);
            adapter = new HomeAdapter(HistoryRecordActivity.this,data);
            rv_his_rec.setLayoutManager(new LinearLayoutManager(HistoryRecordActivity.this));
            rv_his_rec.setAdapter(adapter);
        }
        else {
            error_his_rec.setVisibility(View.VISIBLE);
        }

    }
}