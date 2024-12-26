package com.inven.utils;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.inven.models.User;

public class Koneksi {
    private static final String jdbcurl = "jdbc:mysql://localhost:3306/db_gudang";
    private static final String name = "root";
    private static final String passwd = "";

    private static Connection connection;
    private static String msg;

    private static void setupDriver() {
        try {
            Driver mysqlDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(mysqlDriver);
        } catch (Exception e) {
            // TODO: Handling exception
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            setupDriver();
            return DriverManager.getConnection(jdbcurl, name, passwd);
        } catch (SQLException e) {
            // TODO: Handling exception
            JOptionPane.showMessageDialog(null, "Koneksi Database Gagal: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static String logoutConnection() {
        User.setRoleUser(null);
        User.setCurrentUser(null);
        try {
            closeConnection();
            if (connection == null) {
                msg = "Koneksi ke database tertutup";
            }
        } catch (SQLException e) {
            // TODO: Handling exception
            msg = e.getMessage();
            JOptionPane.showMessageDialog(null, msg);
            e.printStackTrace();
        }
        return msg;
    }
}
