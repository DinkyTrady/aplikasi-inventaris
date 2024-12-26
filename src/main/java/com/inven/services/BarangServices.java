package com.inven.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import com.inven.controller.BarangController;
import com.inven.utils.Koneksi;
import com.inven.utils.TableHelper;

public class BarangServices {
    private String query;

    // untuk menampilkan error message
    private void errMsg(SQLException e) {
        TableHelper.errorMessage("Terdapat Sebuah Error", e);
    }

    public void tambahBarang(boolean isAdmin, String namaBarang, String kategoriBarang, int jumlahBarang,
            String keteranganBarang, BarangController frame) {
        try (Connection conn = Koneksi.getConnection()) {
            query = "INSERT INTO barang (nama_barang, kategori, jumlah, keterangan) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query);) {
                stmt.setString(1, namaBarang);
                stmt.setString(2, kategoriBarang);
                stmt.setInt(3, jumlahBarang);
                stmt.setString(4, keteranganBarang);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Barang berhasil ditambahkan!");
                    frame.addBarang.dispose();
                    isTableAdmin(isAdmin);
                }
                stmt.close();
            }
        } catch (SQLException e) {
            // TODO: handle exception
            errMsg(e);
        }
    }

    // untuk mengupdate user
    public void editBarang(boolean isAdmin, String namaBarang, String kategoriBarang, int jumlahBarang,
            String keteranganBarang, int id,
            BarangController frame) {
        try (Connection conn = Koneksi.getConnection()) {
            query = "UPDATE barang SET nama_barang=?, kategori=?, jumlah=?, keterangan=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, namaBarang);
            stmt.setString(2, kategoriBarang);
            stmt.setInt(3, jumlahBarang);
            stmt.setString(4, keteranganBarang);
            stmt.setInt(5, id);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Barang berhasil diupdate!");
                frame.editBarang.dispose();
                isTableAdmin(isAdmin);
            }
            stmt.close();
        } catch (SQLException e) {
            // TODO: handle exception
            errMsg(e);
        }
    }

    public void hapusBarang(boolean isAdmin, int id) {
        try (Connection conn = Koneksi.getConnection()) {
            query = "DELETE FROM barang WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);
            stmt.executeUpdate();
            isTableAdmin(isAdmin);
            stmt.close();
        } catch (SQLException e) {
            // TODO: handle exception
            errMsg(e);
        }
    }

    private void isTableAdmin(boolean isAdmin) {
        if (!isAdmin) {
            TableHelper.loadTable(true, "barang");
            return;
        }
        TableHelper.loadTable(true, "barang");
        return;
    }
}
