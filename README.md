> [!WARNING]
> This repository archived because it will move to another repository check the new repository
> [Warehouse-Apps](https://github.com/DinkyTrady/Warehouse-App)

# Aplikasi Gudang

Aplikasi ini bertujuan untuk menyimpan sebuah barang atau memantau barang yang masuk

<!-- > [!WARNING] -->
<!-- > Pastikan sebelum menjalankan file script untuk berada di folder projek ini! -->

## Cara Menajalankan Code Java pada projek ini

beberapa tools yang diperlukan (pastikan tools telah terinstall pada sistem kalian)

- Tools

  - mysql
  - maven
  - jdk

- Linux/MacOS/Android (gunakan termux)

  > [!WARNING]
  > Pastikan bahwa mysql root tak mempunyai passwd jika ada passwd pada mysql
  > ganti passwd yang ada pada file `Koneksi.java`

  1. check mysql root memiliki password atau tidak (bisa ganti keusername juga)
     jika ada yang berbeda ganti username atau password pada [Koneksi.java](./src/main/java/com/inven/utils/Koneksi.java)
  2. lakukan source sql file

     ```bash
     # masuk ke mysql
     mysql -u root
     # source file sql
     \. assets/db/db_gudang.sql
     # exit mysql
     exit;
     ```

  3. compile dan running projek projek

     ```bash
     # compile projek (akan otomatis install dependencies)
     mvn compile
     # run projek dengan:
     mvn exec:java -Dexec.mainClass="com.inven.App"
     # bersihkan target projek:
     mvn clean
     ```

- Windows
  not tested yet
