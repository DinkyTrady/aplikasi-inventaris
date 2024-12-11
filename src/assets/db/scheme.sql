create database if not exists db_gudang;
use db_gudang;

-- Table Barang
create table if not exists barang (
    id int auto_increment primary key,
    nama_barang varchar(100) not null,
    kategori varchar(50),
    jumlah int not null,
    harga decimal(10, 2),
    keterangan text
);
