package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class AssociationApplyActivity extends AppCompatActivity {
    //声明控件
    private ImageButton mBtn_back;
    private EditText mEt_student_name;
    private EditText mEt_community_name;
    private EditText mEt_phone_number;
    private Button mBtn_get_verification_code;
    private EditText mEt_upload_photo_evidence;
    private ImageButton mBtn_upload_photo;
    private Button mBtn_button_ensure;

    EventHandler handler;

    DBUtils db;
    ResultSet rs1, rs2;
    Thread t;
    boolean tmp1 = false;
    boolean tmp2 = false;
    String Association_id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MobSDK.submitPolicyGrantResult(true, null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.association_apply_page);

        MobSDK.init(this, "3598b2e09a55a", "48beefd31be58bc75a9cdedda26e67cc");

        //控件部分
        mBtn_back = findViewById(R.id.association_apply_page_1_button_back);
        mEt_student_name = findViewById(R.id.association_apply_page_edittext_student_name);
        mEt_community_name = findViewById(R.id.association_apply_page_edittext_community_name);
        mEt_phone_number = findViewById(R.id.association_apply_page_edittext_phone_number);
        mBtn_get_verification_code = findViewById(R.id.association_apply_page_button_get_verification_code);
        mEt_upload_photo_evidence = findViewById(R.id.association_apply_page_edittext_upload_photo_evidence);
        mBtn_upload_photo = findViewById(R.id.association_apply_page_button_upload_photo);
        mBtn_button_ensure = findViewById(R.id.association_apply_page_button_ensure);

        //返回
        mBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(AssociationApplyActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });
        //验证信息
        handler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "申请成功！", Toast.LENGTH_LONG).show();
                                String sql = "insert into apply(User_phone, Association_id) values ('" + mEt_phone_number.getText().toString() + "', '" + Association_id + "');";
                                DBUtils dbUtils = new DBUtils();
                                try {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            dbUtils.update(sql);
                                        }
                                    }).start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //跳转页面
                                Intent intent = null;
                                intent = new Intent(AssociationApplyActivity.this, HomePage.class);
                                startActivity(intent);
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AssociationApplyActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    //失败回调
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "验证码错误",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(handler);
        //获取验证码
        mBtn_get_verification_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEt_phone_number.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "未输入手机号", Toast.LENGTH_LONG).show();
                } else if (mEt_phone_number.getText().toString().length() > 15 || mEt_phone_number.getText().toString().length() < 10) {
                    Toast.makeText(getApplicationContext(), "手机号格式不对", Toast.LENGTH_LONG).show();
                } else {
                    //如果没问题则发送验证码
                    //Toast.makeText(getApplicationContext(), "验证码已发送", Toast.LENGTH_LONG).show();
                    String phone = mEt_phone_number.getText().toString();
                    SMSSDK.getVerificationCode("86", phone);
                }
            }
        });
        //确认申请
        mBtn_button_ensure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (mEt_student_name.getText().toString().equals("") || mEt_community_name.getText().toString().equals("") || mEt_phone_number.getText().toString().equals("") || mEt_upload_photo_evidence.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "信息不全", Toast.LENGTH_LONG).show();
                } else if (mEt_community_name.getText().toString().length() > 20 || mEt_phone_number.getText().toString().length() > 20) {
                    Toast.makeText(getApplicationContext(), "社团名或手机号不符合规范", Toast.LENGTH_LONG).show();
                } else {
                    t=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            db = new DBUtils();
                            rs1 = db.query("select * from Association where Association_name = '" + mEt_community_name.getText().toString() + "';");
                            try {
                                if (rs1.next()) {
                                    Association_id = rs1.getString("Association_id");
                                    tmp1 = true;
                                    rs1.previous();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    t.start();
                    while(t.isAlive());
                    t=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            db = new DBUtils();
                            rs2 = db.query("select * from User where User_phone = '" + mEt_phone_number.getText().toString() + "';");
                            try {
                                if (rs2.next()) {
                                    tmp2 = true;
                                    rs2.previous();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    t.start();
                    while(t.isAlive());
                    if (tmp1 && tmp2) {
                        String phone = mEt_phone_number.getText().toString();
                        String number = mEt_upload_photo_evidence.getText().toString();
                        SMSSDK.submitVerificationCode("86", phone, number);
                    } else {
                        Toast.makeText(getApplicationContext(), "社团名不存在或个人信息错误", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}