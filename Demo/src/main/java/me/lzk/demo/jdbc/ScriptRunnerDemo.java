package me.lzk.demo.jdbc;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/***
 * @Program: ScratchPaper
 * @Description: a little demo of scriptdemo
 * @Author: Li Zekun
 * @CreateDate: 2021-01-26 16:46
 **/
public class ScriptRunnerDemo {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            String url = "jdbc:hive2://192.168.1.192:10000/sample";
//            Class.forName("com.mysql.jdbc.Connection");
//            String url = "jdbc:mysql://192.168.1.192:3306/sample?useSSL=false";
            String username = "root";
            String password = "root";
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
//        runScript(getConnection(),"test.sql");
        try {
            StringBuilder script = new StringBuilder();
            BufferedReader lineReader = new BufferedReader(new InputStreamReader(new FileInputStream("Demo/src/main/resources/mysql-mapper.sql"), StandardCharsets.UTF_8));
            String line;
            while ((line = lineReader.readLine()) != null) {
                script.append(line);
                script.append(System.getProperty("line.separator", "\n"));
            }
            String command = script.toString();
            System.out.println(command);
            Statement statement = getConnection().createStatement();
            statement.execute(command);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
