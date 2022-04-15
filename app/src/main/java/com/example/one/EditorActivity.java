package com.example.one;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.one.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditorActivity extends AppCompatActivity implements PermissionInterface {
    //声明控件
    private ImageButton mBtn_back;
    private PermissionHelper mPermissionHelper;
    private Button mBtn_release;
    private EditText mEt_title;
    private EditText mEt_text;
    private ImageButton mBtn_insert_picture;
    private ImageView mImg_picture;

    DBUtils db;
    ResultSet rs;
    Thread t;

    Uri photouri;

    ActivityResultLauncher<String> perssion_camera = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
//                        Toast.makeText(getApplicationContext(),"wonderful", Toast.LENGTH_LONG).show();
                        photouri = createImageUri();
                        req_camera.launch(photouri);
                    }
                }
            });
    ActivityResultLauncher<String> perssion_album = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
//                        Toast.makeText(getApplicationContext(),"wonderful", Toast.LENGTH_LONG).show();
                        req_album.launch("image/*");
                    }
                }
            });

    ActivityResultLauncher<Uri>  req_camera = registerForActivityResult(
            new ActivityResultContracts.TakePicture(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        launchImageCrop(photouri);

                    }
                }
            }
    );

    ActivityResultLauncher<String> req_album = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    launchImageCrop(result);

                }
            }
    );

    //裁剪图片注册
    private final ActivityResultLauncher<CropImageResult> mActLauncherCrop =
            registerForActivityResult(new CropImage(), result -> {
                //裁剪之后的图片Uri，接下来可以进行压缩处理
                Bitmap bmp;
                try {
                    bmp = CompressImage.getBitmapFormUri(EditorActivity.this, result);
                    mImg_picture.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_page);
        //初始化并发起权限申请
        mPermissionHelper = new PermissionHelper(this, this);
        mPermissionHelper.requestPermissions();

        //控件部分
        mBtn_back = findViewById(R.id.editor_page_1_button_back);
        mBtn_release =findViewById(R.id.editor_page_1_button_release);
        mEt_title = findViewById(R.id.editor_page_edittext_title);
        mEt_text = findViewById(R.id.editor_page_edittext_text);
        mBtn_insert_picture = findViewById(R.id.editor_page_2_button_insert_picture);
        mImg_picture = findViewById(R.id.editor_page_imageview_picture);

        //返回
        mBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(EditorActivity.this, HomePage.class);
                startActivity(intent);
            }
        });
        mBtn_release.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (mEt_title.getText().toString().equals("") || mEt_text.getText().toString().equals("") ) {
                    Toast.makeText(getApplicationContext(), "标题和内容不能为空", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(mEt_title.getText().toString().length()>40 || mEt_text.getText().toString().length()>500)
                    {
                        Toast.makeText(getApplicationContext(), "标题或内容字数超过最大限制", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "发布成功！", Toast.LENGTH_LONG).show();
                                Date PDate=new Date();
                                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String strDate=dateFormat.format(PDate);
                                String sql = "insert into Forumt(F_title, Forumt_content,Forumt_date,User_phone,User_name,F_likenum,F_collectnum,F_commentnum) " +
                                        "values ('" + mEt_title.getText().toString() + "', '" + mEt_text.getText().toString()+ "','"+strDate+"','"+new SaveSharedPreference().getPhone()+"'" +
                                        ",'"+new SaveSharedPreference().getUsername()+"','"+0+"','"+0+"','"+0+"');";
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
                                intent = new Intent(EditorActivity.this, HomePage.class);
                                startActivity(intent);
                            }
                        });
                    }
                }

            }
        });
        mBtn_insert_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)) {
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

    private void initViews() {
        //已经拥有所需权限，可以放心操作任何东西了
    }

    private void display() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_alertdiag_layout,null);
        Button btncamera = (Button) dialogView.findViewById(R.id.btncamera);
        Button btnalbum = (Button) dialogView.findViewById(R.id.btnalbum);

        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perssion_camera.launch(Manifest.permission.CAMERA);

            }
        });

        btnalbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perssion_album.launch(Manifest.permission.CAMERA);

            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setView(dialogView);
        dialog.show();
    }

    /**
     * 开启裁剪图片
     *
     * @param sourceUri 原图片uri
     */
    private void launchImageCrop(Uri sourceUri) {
        mActLauncherCrop.launch(new CropImageResult(sourceUri, 1, 1));
    }



    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    private Uri createImageUri() {
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        } else {
            return getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        }
    }


}