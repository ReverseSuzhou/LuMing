package com.example.one.util;


import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class describes how to store picture file into MySQL.
 * @author Yanjiang Qian
 * @version 1.0 Jan-02-2006
 */
public class StorePicturesUtil {

    //要连接的数据库url,注意：此处连接的应该是服务器上的MySQl的地址
    private final static String url = "jdbc:mysql://180.76.227.152:3306/wenqv";
    //连接数据库使用的用户名
    private final static String userName = "root";
    //连接的数据库时使用的密码
    private final static String password = "123456";
    Connection connection=null;
    PreparedStatement ps=null;
    ResultSet rs=null;



    //这里的strFile指的是文件的路径
    public void storeImg(String strFile, String Caption){
        //System.out.println("文件路径" + strFile);
        try {
            //1、加载驱动
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("驱动加载成功！！！（图片2）");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            //2、获取与数据库的连接
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("连接数据库成功！！（图片2）");
            //3.sql语句
            //4.获取用于向数据库发送sql语句的ps

            ps = connection.prepareStatement("SELECT MAX(Pic_id) FROM Pic");
            ResultSet rs = ps.executeQuery();
            int id = 0;
            File file = new File(strFile);
            FileInputStream fis = new FileInputStream(file);

            while(rs.next()) {
                id = rs.getInt(1)+1;
            }
            System.out.println("id = " + id);
            ps = connection.prepareStatement("insert "
                    + "into Pic values (?,?,?)"); //三个问号对应下面的三个
            ps.setInt(1, id);  //主键，也就是编号
            ps.setString(2, Caption); //图片描述
            //ps.setString(3, imageString);
            ps.setBinaryStream(3, fis, (int) file.length());  //文件本身
            ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("fail");
        }
        finally {
            if(connection!=null){
                try {
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public ContentResolver getContentResolver() {
        throw new RuntimeException("Stub!");
    }
}

