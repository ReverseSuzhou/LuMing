package com.example.one;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.one.util.StorePicturesUtil;
import com.example.one.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PersonalActivity extends AppCompatActivity {
    //声明控件
    //底下五个
    private RadioButton mBtn_home;
    private RadioButton mBtn_association;
    private RadioButton mBtn_editor;
    private RadioButton mBtn_message;
    private RadioButton mBtn_personal;
    //上面几个
    private ImageButton mBtn_history;
    private ImageButton mBtn_collect;
    private ImageButton mBtn_replace_phone;
    private Button mBtn_modify;
    private Button mBtn_apply;
    private Button mBtn_myrelease;
    private ImageButton mBtn_userpicture;
    TextView UserName;
    Button rBt_cancellation;

    private PermissionHelper mPermissionHelper;

    //更改头像的辅助声明
    int CAMERA_REQUEST_CODE=1;
    int ALBUM_REQUES_CODE=2;

    //用于保存拍照图片的uri
    private Uri mCameraUri;

    // 用于保存图片的文件路径，Android 10以下使用图片路径访问图片
    private String mCameraImagePath;

    // 是否是Android 10以上手机
    private boolean isAndroidQ = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;

    // 申请相机权限的requestCode
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 0x00000012;
    private static final int PERMISSION_ALBUM_REQUEST_CODE = 0x00000013;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_page);
        InitUpButtonAndTheirListeners();
        InitBelowButtonAndTheirListeners();
        UserName.setText(new SaveSharedPreference().getUsername());
    }

    protected void onResume(Bundle savedInstance) {
        super.onResume();
        UserName = findViewById(R.id.personal_page_1_textview_username);
        UserName.setText(new SaveSharedPreference().getUsername());
    }

    private void InitUpButtonAndTheirListeners() {

        mBtn_history = findViewById(R.id.personal_page_2_button_history);
        mBtn_collect = findViewById(R.id.personal_page_2_button_collect);
        mBtn_replace_phone = findViewById(R.id.personal_page_2_button_phone);
        mBtn_modify = findViewById(R.id.personal_page_button_exinfo);
        mBtn_apply = findViewById(R.id.personal_page_button_association_apply);
        mBtn_userpicture = findViewById(R.id.personal_page_1_button_userpicture);
        mBtn_myrelease = findViewById(R.id.personal_page_button_myrelease);
        UserName = findViewById(R.id.personal_page_1_textview_username);
        rBt_cancellation = findViewById(R.id.personal_page_button_cancellation);
        //历史记录
        mBtn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,HistoryRecordActivity.class);
                startActivity(intent);
            }
        });
        //收藏
        mBtn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,CollectActivity.class);
                startActivity(intent);
            }
        });
        //修改手机号
        mBtn_replace_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,ReplacePhoneActivity.class);
                startActivity(intent);
            }
        });
        //修改个人信息
        mBtn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,ModifyPersonalActivity.class);
                startActivity(intent);
            }
        });
        //申请社团
        mBtn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,AssociationApplyActivity.class);
                startActivity(intent);
            }
        });
        //我的帖子
        mBtn_myrelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this, MyReleaseActivity.class);
                startActivity(intent);
            }
        });


        //更改头像
        mBtn_userpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display();
            }
        });

        rBt_cancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveSharedPreference saveSharedPreference = new SaveSharedPreference();
                saveSharedPreference.close();
                Intent intent = null;
                intent = new Intent(PersonalActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void InitBelowButtonAndTheirListeners() {
        //↓↓↓↓↓↓↓↓底下五个按钮的跳转功能↓↓↓↓↓↓↓↓
//使用的时候要修改 ：1.别忘了上面的声明控件部分；2.控件部分中id后面修改成对应的id；3.OnClick中的.this前面的改成当前文件名

        //控件部分
        mBtn_home = findViewById(R.id.rb_mp);
        mBtn_association = findViewById(R.id.rb_association);
        mBtn_editor = findViewById(R.id.rb_add);
        mBtn_message = findViewById(R.id.rb_message);
        mBtn_personal = findViewById(R.id.rb_user);

        //主页面
        mBtn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,HomePage.class);
                startActivity(intent);
            }
        });
        //社团圈
        mBtn_association.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,AssociationActivity.class);
                startActivity(intent);
            }
        });
        //发帖子
        mBtn_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,EditorActivity.class);
                startActivity(intent);
            }
        });
        //消息
        mBtn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });
        //个人信息
        mBtn_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(PersonalActivity.this,PersonalActivity.class);
                startActivity(intent);
            }
        });
//↑↑↑↑↑↑↑↑底下五个按钮的跳转功能↑↑↑↑↑↑↑↑
    }

    private void display() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_alertdiag_layout,null);
        Button btncamera = (Button) dialogView.findViewById(R.id.btncamera);
        Button btnalbum = (Button) dialogView.findViewById(R.id.btnalbum);

        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionAndCamera();
            }
        });

        btnalbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionAndAlbum();
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setView(dialogView);
        dialog.show();
    }

    /**
     * 检查权限并拍照。
     * 调用相机前先检查权限。
     */
    private void checkPermissionAndCamera() {
        int hasCameraPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.CAMERA);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有调起相机拍照。
            openCamera();
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},
                    PERMISSION_CAMERA_REQUEST_CODE);
        }
    }
    private void checkPermissionAndAlbum() {
        int hasCameraPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.CAMERA);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有调起相机拍照。
            openalbum();
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_ALBUM_REQUEST_CODE);
        }
    }


    /**
     * 处理权限申请的回调。
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许权限，有调起相机拍照。
                openCamera();
            } else {
                //拒绝权限，弹出提示框。
                Toast.makeText(this, "拍照权限被拒绝", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == PERMISSION_ALBUM_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许权限，有调起相机拍照。
                openalbum();
            } else {
                //拒绝权限，弹出提示框。
                Toast.makeText(this, "相册权限被拒绝", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 调起相机拍照
     */
    private void openCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断是否有相机
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            Uri photoUri = null;

            if (isAndroidQ) {
                // 适配android 10
                photoUri = createImageUri();
            } else {
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    mCameraImagePath = photoFile.getAbsolutePath();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                        photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                    } else {
                        photoUri = Uri.fromFile(photoFile);
                    }
                }
            }

            mCameraUri = photoUri;
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(captureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }


    private void openalbum() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent,ALBUM_REQUES_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (isAndroidQ) {
                    // Android 10 使用图片uri加载
                    mBtn_userpicture.setImageURI(mCameraUri);
                } else {
                    // 使用图片路径加载
                    mBtn_userpicture.setImageBitmap(BitmapFactory.decodeFile(mCameraImagePath));
                }
            } else {
                Toast.makeText(this,"取消",Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == ALBUM_REQUES_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                mBtn_userpicture.setImageURI(uri);

                //以下方法将获取的uri转为String类型哦！
                String []imgs={MediaStore.Images.Media.DATA};//将图片URI转换成存储路径
                Cursor cursor=this.managedQuery(uri, imgs, null, null, null);
                int index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path=cursor.getString(index);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showMsg(PersonalActivity.this, path);
                                    StorePicturesUtil storePicturesUtil = new StorePicturesUtil();
                                    storePicturesUtil.storeImg(path, "这里写文件的名字");
                                }
                            }).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }});

            }
        }
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

    /**
     * 创建保存图片的文件
     */
    private File createImageFile() throws IOException {
        String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File tempFile = new File(storageDir, imageName);
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }

}