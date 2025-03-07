package com.inven.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.inven.controller.BarangController;
import com.inven.gui.AdminDashboard;
import com.inven.gui.Login;
import com.inven.models.User;
import com.inven.utils.Koneksi;

public class AuthServices {
    public void login(String username, String passwd, Login login) {
        try (Connection conn = Koneksi.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?"; // for database sql
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, username);
            stmt.setString(2, passwd);

            // Pengecekan query jika user mempunyai role admin
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                User.setCurrentUser(username);
                login.dispose();
                if ("admin".equalsIgnoreCase(role)) {
                    User.setRoleUser("admin");
                    new AdminDashboard().setVisible(true);
                } else {
                    User.setRoleUser("users");
                    new BarangController().setVisible(true);
                }
            } else {
                // will show message if username or password is wrong
                JOptionPane.showMessageDialog(null, "username atau password salah! \nCoba Lagi!");
            }
        } catch (SQLException e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
