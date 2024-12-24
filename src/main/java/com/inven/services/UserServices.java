package com.inven.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.inven.controller.AdminUserController;
import com.inven.utils.Koneksi;
import com.inven.utils.TableHelper;

public class UserServices {
    private String query;

    // untuk menampilkan error message
    private void errMsg(SQLException e) {
        TableHelper.errorMessage("Terdapat Sebuah Error: ", e);
    }

    public void addNewUser(String username, String passwd, String role, AdminUserController frame) {
        TableHelper.isEmpty(username, passwd);

        try (Connection conn = Koneksi.getConnection()) {
            query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query);) {
                stmt.setString(1, username);
                stmt.setString(2, passwd);
                stmt.setString(3, role);

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "User berhasil ditambahkan!");
                frame.addNewUser.dispose();
                TableHelper.loadTable(false, true, "users");
                stmt.close();
            }
        } catch (SQLException e) {
            // TODO: handle exception
            errMsg(e);
        }
    }

    // untuk mengupdate user
    public void updateUser(String username, String passwd, String role, int id, AdminUserController frame) {
        TableHelper.isEmpty(username, passwd);

        try (Connection conn = Koneksi.getConnection()) {
            query = "UPDATE users SET username=?, password=?, role=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, username);
            stmt.setString(2, passwd);
            stmt.setString(3, role);
            stmt.setInt(4, id);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "User berhasil diupdate!");
                frame.editUserFrame.dispose();
                TableHelper.loadTable(false, true, "users");
            }
            stmt.close();
        } catch (SQLException e) {
            // TODO: handle exception
            errMsg(e);
        }
    }

    public void deleteUser(int id) {
        try (Connection conn = Koneksi.getConnection()) {
            query = "DELETE FROM users WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);
            stmt.executeUpdate();
            TableHelper.loadTable(false, true, "users");
            stmt.close();
        } catch (SQLException e) {
            // TODO: handle exception
            errMsg(e);
        }
    }
}
