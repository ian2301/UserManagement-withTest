package com.example.notUsedApp;

import com.example.pojo.entity.SqlUser;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


import javax.xml.transform.sax.SAXResult;
import java.sql.*;
import java.util.ArrayList;

/**
 * 用原始的JDBC方式实现数据库的增删查改
 * **** 未使用***
 */

public class DataOperation {

    //@SuppressWarnings("all")的作用：可以让编译器不再提醒有重复行的警告
//    @SuppressWarnings("all")
    //把连接对象变成类成员，这样每次进行单元测试的时候就避免了再次写连接数据库的代码部分
    private Connection connection = null;


    //    ****连接数据库
    //@Before注解：在执行@Test注解之前先执行before注解
    @BeforeEach
    public void init() throws Exception {
        //1.加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取连接对象
        connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/my_data",
                "root",
                "321654");
    }

    @AfterEach
    public void close() throws Exception{

        connection.close();
    }


    //  ******  增加 数据
    public void add() throws Exception {
        //1.创建sql语句模版
        String sql = " INSERT INTO app_user VALUES(NULL, ?, ?,?,?);";
        //2.获取能够执行sql语句的对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //占位符为问号，即？
        //3.给sql模板中的占位符赋值
        preparedStatement.setString(1, "user_name");
        preparedStatement.setString(2, "password");
        preparedStatement.setString(3, "first_name");
        preparedStatement.setString(4, "last_Name");
        //4.执行sql
        preparedStatement.execute();
        //5. 关闭
        preparedStatement.close();

    }

    //  *****  删除by ID
    public void dele(Integer id) throws Exception {
        String sql = "DELETE FROM app_user WHERE ID=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
       // preparedStatement.setLong(id);  // id: 传进来
        preparedStatement.executeUpdate();

        preparedStatement.close();
        // connection.close();
    }

    //    **** 修改更新 by ID
    public void update() throws Exception {
        String sql = "UPDATE app_user id=? WHERE  user_name=？;";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "id");
        preparedStatement.setString(2, "user_name");

        preparedStatement.executeUpdate();

        preparedStatement.close();
       //  connection.close();

    }

    //    **** 查找 by ID
    public void findById() throws Exception {
        //1.创建sql语句模版
        String sql = "SELECT * FROM app_user WHERE ID =?;";
        //2.获取能够执行sql语句的对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //占位符为问号，即？
        //3.给sql模板中的占位符赋值
        preparedStatement.setLong(1, 2);
        //4.执行sql,返回结果集 resultSet
        ResultSet resultSet = preparedStatement.executeQuery();

        //在rs.next()指向的这条查询结果中，通过对应的列序号取值
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);

            //在resultSet.next()指向的这条查询结果中，通过对应的列名取值
            //int did = rs.getInt("id");
            //String dname = rs.getString("name");
            //String location = rs.getString("password");

            System.out.println("id:" +id +"name: "+name+"password:" +password);
        }

        resultSet.close();
        preparedStatement.close();
        // connection.close();
    }

//    **** 查询所有数据
    public void list() throws Exception{

        ArrayList<?> list=new ArrayList<>();

        String sql = "SELECT * FROM app_user";

        PreparedStatement preparedStatement =connection.prepareStatement(sql);

        ResultSet resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("user_name");
            String password = resultSet.getString("password");

            System.out.println("id:" +id +"name: "+name+"password:" +password);

            //SqlUser sqlUser=new SqlUser(id,name,password);

            //list.add(sqlUser);
        }

        resultSet.close();
        preparedStatement.close();
        // connection.close();
    }
}
