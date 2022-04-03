package com.example.one;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.one.util.ToastUtil;

public class EditorActivity extends AppCompatActivity implements PermissionInterface{
    //声明控件
    private ImageButton mBtn_back;
    private PermissionHelper mPermissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_page);
        //初始化并发起权限申请
        mPermissionHelper = new PermissionHelper(this, this);
        mPermissionHelper.requestPermissions();

        //控件部分
        mBtn_back = findViewById(R.id.editor_page_1_button_back);

        //返回
        mBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(EditorActivity.this,HomePage.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)){
            //权限请求结果，并已经处理了该回调
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public int getPermissionsRequestCode() {
        //设置权限请求requestCode，只有不跟onRequestPermissionsResult方法中的其他请求码冲突即可。
        return 10000;
    }

    @Override
    public String[] getPermissions() {
        //设置该界面所需的全部权限
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, //存储权限
                Manifest.permission.CAMERA, //相机权限
                Manifest.permission.READ_CALENDAR
        };
    }

    @Override
    public void requestPermissionsSuccess() {
        //权限请求用户已经全部允许
        initViews();
    }

    @Override
    public void requestPermissionsFail() {
        //权限请求不被用户允许。可以提示并退出或者提示权限的用途并重新发起权限申请。
        //finish(); //这里就直接闪退了
        ToastUtil.showMsg(EditorActivity.this, "建议打开相应的权限");
        mPermissionHelper = new PermissionHelper(this, this);
        mPermissionHelper.requestPermissions();
    }

    private void initViews(){
        //已经拥有所需权限，可以放心操作任何东西了
    }

}