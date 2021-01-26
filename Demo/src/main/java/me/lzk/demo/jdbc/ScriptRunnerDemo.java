package me.lzk.demo.jdbc;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/***
 * @Program: ScratchPaper
 * @Description: a little demo of scriptdemo
 * @Author: Li Zekun
 * @CreateDate: 2021-01-26 16:46
 **/
public class ScriptRunnerDemo {

    private static String url = "jdbc:mysql://192.168.1.192:3306/sample?useSSL=false";
    private static String username = "root";
    private static String password = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Connection");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void runScript(Connection connection, String sqlPath) {
        try {
            ScriptRunner runner = new ScriptRunner(connection);
            Resources.setCharset(StandardCharsets.UTF_8);
            runner.runScript(Resources.getResourceAsReader(sqlPath));
            runner.closeConnection();
            connection.close();
        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
        }
    }

    public static void main(String[] args) {
        runScript(getConnection(),"test.sql");
    }
}
