create database if not exists db_gudang;
use db_gudang;

-- Table Barang
create table if not exists barang (
    id int AUTO_INCREMENT PRIMARY KEY,
    nama_barang varchar(255) NOT NULL,
    kategori varchar(255),
    jumlah int not null check (jumlah >= 0),
    status VARCHAR(10) as (case when jumlah = 0 then 'Kosong' else 'Terserdia' end) stored,
    keterangan text,
    barang_masuk DATETIME DEFAULT CURRENT_TIMESTAMP,
    barang_update DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Table users
create table if not exists users (
    id int auto_increment primary key,
    username varchar(255) not null unique,
    password varchar(255) not null,
    role enum('user', 'admin') not null
);

-- Insert default Barang
insert into barang (nama_barang, kategori, jumlah, keterangan) values 
  ('Tempe', 'Sandang Pangan', 2, "Tempe terbaik yang kumiliki");

-- Insert users
insert into users (username, password, role) values
('admin', 'admin123', 'admin'),
('hafizh', 'hfzp123', 'user');
