package com.inven;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class App {
    private JFrame frame;
    private JTextField txtNamaBarang;
    private JTextField txtKategori;
    private JTextField txtJumlah;
    private JTextField txtHarga;
    private JTextArea txtKeterangan;
    private JTable table;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                App window = new App();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public App() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Aplikasi Inventaris");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Panel untuk form input
        JPanel panelInput = new JPanel();
        frame.getContentPane().add(panelInput, BorderLayout.NORTH);
        panelInput.setLayout(new GridLayout(6, 2));

        panelInput.add(new JLabel("Nama Barang:"));
        txtNamaBarang = new JTextField();
        panelInput.add(txtNamaBarang);

        panelInput.add(new JLabel("Kategori:"));
        txtKategori = new JTextField();
        panelInput.add(txtKategori);

        panelInput.add(new JLabel("Jumlah:"));
        txtJumlah = new JTextField();
        panelInput.add(txtJumlah);

        panelInput.add(new JLabel("Harga:"));
        txtHarga = new JTextField();
        panelInput.add(txtHarga);

        panelInput.add(new JLabel("Keterangan:"));
        txtKeterangan = new JTextArea(3, 20);
        panelInput.add(new JScrollPane(txtKeterangan));

        // Panel untuk tombol
        JPanel panelTombol = new JPanel();
        frame.getContentPane().add(panelTombol, BorderLayout.CENTER);

        JButton btnTambah = new JButton("Tambah");
        panelTombol.add(btnTambah);

        JButton btnUpdate = new JButton("Update");
        panelTombol.add(btnUpdate);

        JButton btnHapus = new JButton("Hapus");
        panelTombol.add(btnHapus);

        // Panel untuk tabel
        JPanel panelTabel = new JPanel();
        frame.getContentPane().add(panelTabel, BorderLayout.SOUTH);
        panelTabel.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(
                new String[] { "ID", "Nama Barang", "Kategori", "Jumlah", "Harga", "Keterangan" }, 0);
        table = new JTable(tableModel);
        panelTabel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Tambah data barang ke database
        btnTambah.addActionListener(e -> {
            String namaBarang = txtNamaBarang.getText();
            String kategori = txtKategori.getText();
            String jumlahStr = txtJumlah.getText();
            String hargaStr = txtHarga.getText();
            String keterangan = txtKeterangan.getText();

            try (Connection con = Koneksi.getConnection()) {
                String query = "INSERT INTO barang (nama_barang, kategori, jumlah, harga, keterangan) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = con.prepareStatement(query)) {
                    stmt.setString(1, namaBarang);
                    stmt.setString(2, kategori);
                    stmt.setInt(3, Integer.parseInt(jumlahStr));
                    stmt.setBigDecimal(4, new java.math.BigDecimal(hargaStr));
                    stmt.setString(5, keterangan);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Data berhasil ditambahkan!");
                    refreshTable();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Update data barang
        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) table.getValueAt(selectedRow, 0);
                String namaBarang = txtNamaBarang.getText();
                String kategori = txtKategori.getText();
                String jumlahStr = txtJumlah.getText();
                String hargaStr = txtHarga.getText();
                String keterangan = txtKeterangan.getText();

                try (Connection con = Koneksi.getConnection()) {
                    String query = "UPDATE barang SET nama_barang=?, kategori=?, jumlah=?, harga=?, keterangan=? WHERE id=?";
                    try (PreparedStatement stmt = con.prepareStatement(query)) {
                        stmt.setString(1, namaBarang);
                        stmt.setString(2, kategori);
                        stmt.setInt(3, Integer.parseInt(jumlahStr));
                        stmt.setBigDecimal(4, new java.math.BigDecimal(hargaStr));
                        stmt.setString(5, keterangan);
                        stmt.setInt(6, id);
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(frame, "Data berhasil diupdate!");
                        refreshTable();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Pilih data terlebih dahulu untuk diupdate.");
            }
        });

        // Hapus data barang
        btnHapus.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) table.getValueAt(selectedRow, 0);

                try (Connection con = Koneksi.getConnection()) {
                    String query = "DELETE FROM barang WHERE id=?";
                    try (PreparedStatement stmt = con.prepareStatement(query)) {
                        stmt.setInt(1, id);
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(frame, "Data berhasil dihapus!");
                        refreshTable();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Pilih data terlebih dahulu untuk dihapus.");
            }
        });

        // Refresh Tabel
        refreshTable();
    }

    private void refreshTable() {
        // Clear tabel
        tableModel.setRowCount(0);

        try (Connection con = Koneksi.getConnection()) {
            String query = "SELECT * FROM barang";
            try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Object[] row = new Object[] {
                            rs.getInt("id"),
                            rs.getString("nama_barang"),
                            rs.getString("kategori"),
                            rs.getInt("jumlah"),
                            rs.getBigDecimal("harga"),
                            rs.getString("keterangan")
                    };
                    tableModel.addRow(row);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
