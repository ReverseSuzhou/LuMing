package com.example.one.util;


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

//    public DBUtils() {
//        try {
//            //1、加载驱动
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            System.out.println("驱动加载成功！！！");
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public ResultSet query(String strFile){
        try {
            //1、加载驱动
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("驱动加载成功！！！");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            //2、获取与数据库的连接
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("连接数据库成功！！！");
            //3.sql语句
            //4.获取用于向数据库发送sql语句的ps

            int id = 0;
            File file = new File(strFile);
            FileInputStream fis = new FileInputStream(file);
            ps = connection.prepareStatement("SELECT MAX(idpic) FROM Pic");
            ps = connection.prepareStatement("insert "
                    + "into Pic values (?,?,?)"); //三个问号对应下面的三个
            ps.setInt(1, id);  //主键，也就是编号
            ps.setString(2, file.getName()); //图片描述
            ps.setBinaryStream(3, fis, (int) file.length());  //文件本身
            rs = ps.executeQuery();
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(connection!=null){
                try {

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return rs;
    }

    public void storeImg(String strFile){
        try {
            //1、加载驱动
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("驱动加载成功！！！");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            //2、获取与数据库的连接
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("连接数据库成功！！");
            //3.sql语句
            //4.获取用于向数据库发送sql语句的ps

            ps = connection.prepareStatement("SELECT MAX(idpic) FROM Pic");
            ResultSet rs = ps.executeQuery();

            int id = 0;
            File file = new File(strFile);
            FileInputStream fis = new FileInputStream(file);
            if(rs != null) {
                while(rs.next()) {
                    id = rs.getInt(1)+1;
                }
            } else {
                //失败
                //return written;
            }

            ps = connection.prepareStatement("insert "
                    + "into Pic values (?,?,?)"); //三个问号对应下面的三个
            ps.setInt(1, id);  //主键，也就是编号
            ps.setString(2, file.getName()); //图片描述
            ps.setBinaryStream(3, fis, (int) file.length());  //文件本身
            ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
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


}

