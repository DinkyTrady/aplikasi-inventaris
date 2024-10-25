package src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
  static ArrayList<Barang> inventaris = new ArrayList<>();
  static Scanner input = new Scanner(System.in);
  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm EEEE, dd-MMMM-yyyy");

  public static void main(String[] args) {
    while (true) {
      System.out.println("\n----- Aplikasi inventaris-----");
      System.out.println("1. Tambah barang");
      System.out.println("2. Lihat semua barang");
      System.out.println("3. Update barang");
      System.out.println("4. Hapus barang");
      System.out.println("5. Keluar");
      System.out.print("Pilih Menu: ");
      int pilih = input.nextInt();
      input.nextLine();

      switch (pilih) {
        case 1:
          tambahBarang();
          break;
        case 2:
          lihatSemuaBarang();
          break;
        case 3:
          updateBarang();
          break;
        case 4:
          hapusBarang();
          break;
        case 5:
          System.out.println("Keluar dari Aplikasi inventaris");
          return;
        default:
          System.out.println("Pilihan tidak valid, silahkan pilih ulang");
      }
      ;
    }
  };

  public static void tambahBarang() {
    System.out.print("\nMasukan ID barang: ");
    int id = input.nextInt();
    input.nextLine();
    for (Barang barang : inventaris) {
      if (id == barang.id) {
        System.out.println("\nMaaf ID yang diinput telah terdaftar");
        System.out.println("Berikut list ID yang telah terdaftar: ");
        inventaris.forEach(brg -> System.out.print("[ " + brg.id + " ]"));
        System.out.println();
        return;
      }
    }

    System.out.print("Masukan nama barang: ");
    String namaBarang = input.nextLine();
    System.out.print("Masukan jumlah barang: ");
    int jumlahBarang = input.nextInt();
    input.nextLine();
    System.out.print("Masukan deskripsi barang: ");
    String deskripsiBarang = input.nextLine();
    System.out.print("Masukan nama penginput barang: ");
    String namaPenginput = input.nextLine();

    LocalDateTime getDate = LocalDateTime.now();
    String dateInput = getDate.format(formatter);
    Barang barangBaru = new Barang(id, jumlahBarang, namaBarang, namaPenginput, deskripsiBarang, dateInput);
    inventaris.add(barangBaru);

    System.out.println("\nBarang berhasil ditambahkan!");
    System.out.println(barangBaru);
  }

  public static void lihatSemuaBarang() {
    if (inventaris.isEmpty()) {
      System.out.println("\nBelum ada barang yang ditambahkan!");
    } else {
      inventaris.forEach(barang -> {
        System.out.println("\n{");
        System.out.println(barang);
        System.out.println("}");
      });
    }
  }

  public static void updateBarang() {
    System.out.print("\nMasukan ID barang yang ingin diperbarui: ");
    int id = input.nextInt();
    for (Barang brg : inventaris) {
      if (id == brg.id) {
        System.out.print("\nPerabarui jumlah barang: ");
        brg.jumlahBarang = input.nextInt();
        input.nextLine();
        System.out.print("Perbarui deskripsi barang: ");
        brg.deskripsiBarang = input.nextLine();
        System.out.print("Masukan nama anda: ");
        brg.namaPengupdate = input.nextLine();
        brg.dateUpdated = LocalDateTime.now().format(formatter);

        System.out.println("\nBarang berhasil diperbarui!");
        System.out.println(brg);
        return;
      }
    }

    System.out.println("\nBarang dengan ID [" + id + "] tidak ditemukan!");
    System.out.println();
  }

  public static void hapusBarang() {
    System.out.print("\nMasukan ID barang yang ingin dihapus: ");
    int id = input.nextInt();
    Boolean ketemu = false;

    for (int i = 0; i < inventaris.size(); i++) {
      if (id == inventaris.get(i).id) {
        System.out.println("\nBarang ditemukan \n" + inventaris.get(i));
        ketemu = true;

        System.out.print("\nApakah anda yakin ingin menghapus barang tersebut? [y/n] ");
        input.nextLine();
        String konfirmasi = input.nextLine();

        if (konfirmasi.equalsIgnoreCase("y")) {
          inventaris.remove(i);
          System.out.println("Barang berhasi dihapus!");
        } else {
          System.out.println("Barang tidak dihapus!");
        }
      }
    }

    if (!ketemu) {
      System.out.println("Barang dengan ID [" + id + "] tidak ditemukan!");
    }
  }
}
