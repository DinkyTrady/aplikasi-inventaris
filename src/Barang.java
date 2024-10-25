package src;

class Barang {
  int id;
  String namaBarang;
  int jumlahBarang;
  String deskripsiBarang;
  String namaPenginput;
  String namaPengupdate;
  String dateInput;
  String dateUpdated;

  Barang(int id, int jumlahBarang, String namaBarang, String namaPenginput, String deskripsiBarang, String dateInput) {
    this.id = id;
    this.jumlahBarang = jumlahBarang;
    this.namaBarang = namaBarang;
    this.deskripsiBarang = deskripsiBarang;
    this.namaPenginput = namaPenginput;
    this.namaPengupdate = "-";
    this.dateInput = dateInput;
    this.dateUpdated = null;
  }

  @Override
  public String toString() {
    String status = jumlahBarang > 0 ? "Tersedia" : "Kosong";
    return "ID Barang: " + id + "\nNama Barang: " + namaBarang + "\nJumlah barang: " + jumlahBarang
        + "\nDeskripsi barang: " + deskripsiBarang
        + "\nStatus barang: " + status + "\nPenginput barang: " + namaPenginput + "\nPengupdate barang: "
        + namaPengupdate
        + "\nTanggal barang masuk: " + dateInput + "\nTanggal barang diupdate: "
        + (dateUpdated != null ? dateUpdated : "-");
  }
}
