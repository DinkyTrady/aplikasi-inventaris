package com.inven.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.inven.models.User;

public class TableHelper {
    String query;

    private static DefaultTableModel tableContainer = new DefaultTableModel();

    public static void loadTable(boolean isBarang, String table) {
        boolean isAdmin = User.getRoleUser() == "admin" ? true : false;
        resetTable(isBarang, isAdmin);

        try (Connection conn = Koneksi.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from " + table);

            while (rs.next()) {
                tableContainer.addRow(isiDataTable(rs, isBarang, isAdmin));
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            // TODO: handle exception
            errorMessage("Terdapat Error: ", e);
        }
    }

    private static void resetTable(boolean isBarang, boolean isAdmin) {
        tableContainer.setRowCount(0);

        setColumn(isBarang, isAdmin);
    }

    private static void setColumn(boolean isBarang, boolean isAdmin) {
        String[] columnRows;
        if (isBarang && isAdmin) {
            columnRows = new String[] { "ID", "Nama", "Kategori", "Jumlah", "Status", "Keterangan", "Tanggal Masuk",
                    "Tanggal Update" };
        } else if (!isBarang && isAdmin) {
            columnRows = new String[] { "ID", "Username", "Password", "Roles" };
        } else {
            columnRows = new String[] { "ID", "Nama", "Kategori", "Jumlah", "Status", "Keterangan" };
        }

        tableContainer.setColumnIdentifiers(columnRows);
    }

    private static Object[] isiDataTable(ResultSet rs, boolean isBarang, boolean isAdmin) throws SQLException {
        Object[] rowData;

        if (isBarang && isAdmin) {
            rowData = new Object[] {
                    rs.getInt("id"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori"),
                    rs.getString("jumlah"),
                    rs.getString("status"),
                    rs.getString("keterangan"),
                    rs.getDate("barang_masuk"),
                    rs.getDate("barang_update"),
            };
        } else if (!isBarang && isAdmin) {
            rowData = new Object[] {
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
            };
        } else {
            rowData = new Object[] {
                    rs.getInt("id"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori"),
                    rs.getString("jumlah"),
                    rs.getString("status"),
                    rs.getString("keterangan"),
            };
        }

        return rowData;
    }

    public static void isEmpty(String username, String passwd) {
        if (username.isEmpty() || passwd.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field wajib diisi!");
            return;
        }
    }

    public static DefaultTableModel getTableModel() {
        return tableContainer;
    }

    // untuk menampilkan error message
    public static void errorMessage(String message, SQLException e) {
        JOptionPane.showMessageDialog(null, message + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
