package com.example.one;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;



import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.one.Adapter.HomeAdapter;
import com.example.one.Bean.Push;

import org.xml.sax.SAXException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    //声明控件
    private RadioButton mBtn_home;
    private RadioButton mBtn_association;
    private RadioButton mBtn_editor;
    private RadioButton mBtn_message;
    private RadioButton mBtn_personal;
    private ImageButton mBtn_search;
    private SwipeRefreshLayout swipe_home;
    private RecyclerView rv_home;
    private TextView error_home;
    List<Push> data = new LinkedList<>();
    private HomeAdapter adapter;
    DBUtils db;
    ResultSet rs;
    Thread t;

    private Button hotspot;
    private Button reco;
    private Button study;
    private Button sport;


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
                intent = new Intent(HomePage.this,SearchActivity.class);
                startActivity(intent);
            }
        });



//↓↓↓↓↓↓↓↓底下五个按钮的跳转功能↓↓↓↓↓↓↓↓
//使用的时候要修改 ：1.别忘了上面的声明控件部分；2.控件部分中id后面修改成对应的id；3.OnClick中的.this前面的改成当前文件名

        //控件部分
        mBtn_home = findViewById(R.id.rb_mp);
        mBtn_association = findViewById(R.id.rb_association);
        mBtn_editor = findViewById(R.id.rb_add);
        mBtn_message = findViewById(R.id.rb_message);
        mBtn_personal = findViewById(R.id.rb_user);
        swipe_home = findViewById(R.id.swipe_home);
        rv_home = findViewById(R.id.rv_home);
        error_home = findViewById(R.id.error_home);
        hotspot = findViewById(R.id.main_page_2_button_HotSpot);
        reco = findViewById(R.id.main_page_2_button_Recommend);
        study = findViewById(R.id.main_page_2_button_Study);
        sport = findViewById(R.id.main_page_2_button_Sport);






        swipe_home.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Refresh();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
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
                intent.putExtra("username", new SaveSharedPreference().getUsername());
                startActivity(intent);
            }
        });
        hotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipe_home.setRefreshing(false);
                data.clear();
                try {
                    t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            db = new DBUtils();
                            rs = db.query("select * from forumt order by F_likenum desc;");
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
                                    po.setUser_phone(rs.getString("User_phone"));
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
        });
        reco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipe_home.setRefreshing(false);
                data.clear();
                try {
                    t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            db = new DBUtils();
                            rs = db.query("select * from forumt order by Forumt_date desc;");
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
                                    po.setUser_phone(rs.getString("User_phone"));
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
        });
        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipe_home.setRefreshing(false);
                data.clear();
                try {
                    t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            db = new DBUtils();
                            rs = db.query("select * from forumt where F_lable = 'study' order by Forumt_date desc;");
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
                                    po.setUser_phone(rs.getString("User_phone"));
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
        });
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipe_home.setRefreshing(false);
                data.clear();
                try {
                    t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            db = new DBUtils();
                            rs = db.query("select * from forumt where F_lable = 'sport' order by Forumt_date desc;");
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
                                    po.setUser_phone(rs.getString("User_phone"));
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
        });
        try {
            Refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

//↑↑↑↑↑↑↑↑底下五个按钮的跳转功能↑↑↑↑↑↑↑↑


    }

    private void Refresh() throws SQLException {
        swipe_home.setRefreshing(false);
        data.clear();
        try {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    db = new DBUtils();
                    rs = db.query("select * from forumt order by Forumt_date desc;");
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
                            po.setUser_phone(rs.getString("User_phone"));
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