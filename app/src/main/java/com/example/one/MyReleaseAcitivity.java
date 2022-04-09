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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MyReleaseAcitivity extends AppCompatActivity {
    //声明控件
    private ImageButton mBtn_back;
    private ImageButton mBtn_refresh;
    private SwipeRefreshLayout swipe_myrelease;
    private RecyclerView rv_myrelease;
    private TextView error_myrelease;
    private List<Push> data = new LinkedList<>();
    private HomeAdapter adapter;
    DBUtils db;
    ResultSet rs;
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myrelease_page);

        //控件部分
        mBtn_back = findViewById(R.id.myrelease_page_1_button_back);
        mBtn_refresh = findViewById(R.id.myrelease_page_1_1_button_refresh);

        swipe_myrelease = findViewById(R.id.swipe_myrelease);
        rv_myrelease = findViewById(R.id.rv_myrelease);
        error_myrelease = findViewById(R.id.error_myrelease);

        swipe_myrelease.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_myrelease.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Refresh();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        //返回
        mBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MyReleaseAcitivity.this,PersonalActivity.class);
                startActivity(intent);
            }
        });
        mBtn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Refresh();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        try {
            Refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    private void Refresh() throws SQLException{

        swipe_myrelease.setRefreshing(false);

        data.clear();
        try {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    db = new DBUtils();
                    SaveSharedPreference saveSharedPreference = new SaveSharedPreference();
                    rs = db.query("select * from forumt where User_phone = '" + saveSharedPreference.getPhone() + "';");
                    try {
                        while(rs.next()){
                            Push po = new Push();
                            po.setForumt_id(rs.getString("Forumt_id"));
                            po.setForumt_date(rs.getString("Forumt_date"));
                            po.setForumt_title(rs.getString("F_title"));
                            po.setForumt_content(rs.getString("Forumt_content"));
                            po.setF_likenum(rs.getInt("F_likenum"));
                            po.setF_collectnum(rs.getInt("F_collectnum"));
                            po.setF_commentnum(rs.getInt("F_commentnum"));
                            po.setUsername(rs.getString("User_name"));
                            data.add(po);
                        };
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        db.connection.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            });
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(t.isAlive() == true);
        if(data.size()>0){
            swipe_myrelease.setRefreshing(false);
            swipe_myrelease.setVisibility(View.VISIBLE);
            adapter = new HomeAdapter(MyReleaseAcitivity.this,data);
            rv_myrelease.setLayoutManager(new LinearLayoutManager(MyReleaseAcitivity.this));
            rv_myrelease.setAdapter(adapter);
        }
        else {
            error_myrelease.setVisibility(View.VISIBLE);
        }

    }
}