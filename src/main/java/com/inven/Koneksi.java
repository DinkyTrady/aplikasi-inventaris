package com.inven;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Koneksi {
    private static final String jdbcurl = "jdbc:mysql://localhost:3306/db_gudang";
    private static final String name = "root";
    private static final String passwd = "";

    private static void setupDriver() {
        try {
            Driver mysqlDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(mysqlDriver);
            System.out.println("Driver succesfully connected");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        setupDriver();
        return DriverManager.getConnection(jdbcurl, name, passwd);
    }
}
