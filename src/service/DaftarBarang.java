package service;

import exception.BarangTidakTersediaException;
import java.util.ArrayList;
import java.util.Scanner;
import model.Barang;
import model.SepedaListrik;
import model.SkuterListrik;
import model.SkuterPremium;

/**
 * Daftar seluruh barang sewa dalam sistem, beserta menu CLI untuk mengelolanya.
 */
public class DaftarBarang {
    private ArrayList<Barang> barang = new ArrayList<>();

    /**
     * Menambahkan barang baru.
     *
     * @param b barang yang ditambahkan
     */
    public void tambahBarang(Barang b) {
        barang.add(b);
    }

    /**
     * Mencari barang berdasarkan ID.
     *
     * @param id ID barang
     * @return barang yang ditemukan
     * @throws BarangTidakTersediaException jika ID tidak ditemukan
     */
    public Barang cariBarang(String id) throws BarangTidakTersediaException {
        for (Barang b : barang) {
            if (b.getId().equalsIgnoreCase(id)) {
                return b;
            }
        }
        throw new BarangTidakTersediaException("Barang dengan ID " + id + " tidak ditemukan");
    }

    /**
     * @param id ID barang
     * @return true jika ID sudah dipakai
     */
    public boolean adaId(String id) {
        for (Barang b : barang) {
            if (b.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Mengubah nama dan tarif barang.
     *
     * @param id        ID barang
     * @param namaBaru  nama baru
     * @param tarifBaru tarif harian baru
     * @throws BarangTidakTersediaException jika ID tidak ditemukan
     */
    public void editBarang(String id, String namaBaru, double tarifBaru) throws BarangTidakTersediaException {
        Barang b = cariBarang(id);
        b.setNama(namaBaru);
        b.setTarifHarian(tarifBaru);
    }

    /**
     * Menghapus barang. Barang yang sedang disewa tidak boleh dihapus.
     *
     * @param id ID barang
     * @throws BarangTidakTersediaException jika ID tidak ditemukan atau barang sedang disewa
     */
    public void hapusBarang(String id) throws BarangTidakTersediaException {
        Barang b = cariBarang(id);
        if (!b.isTersedia()) {
            throw new BarangTidakTersediaException("Barang " + b.getNama() + " sedang disewa, tidak bisa dihapus");
        }
        barang.remove(b);
    }

    /**
     * @return seluruh barang
     */
    public ArrayList<Barang> getSemuaBarang() {
        return barang;
    }

    /**
     * @return barang yang tidak sedang disewa
     */
    public ArrayList<Barang> getBarangTersedia() {
        ArrayList<Barang> hasil = new ArrayList<>();
        for (Barang b : barang) {
            if (b.isTersedia()) {
                hasil.add(b);
            }
        }
        return hasil;
    }

    /**
     * Menampilkan menu CLI untuk menambah, mengedit, dan menghapus barang.
     *
     * @param sc scanner untuk input
     */
    public void menuKelola(Scanner sc) {
        boolean lagi = true;
        while (lagi) {
            System.out.println("\n=== KELOLA BARANG ===");
            System.out.println("1. Lihat Semua Barang");
            System.out.println("2. Tambah Barang");
            System.out.println("3. Edit Barang");
            System.out.println("4. Hapus Barang");
            System.out.println("5. Kembali");
            System.out.print("Pilih menu: ");
            String pilih = sc.nextLine().trim();
            try {
                switch (pilih) {
                    case "1":
                        tampilkan(barang);
                        break;
                    case "2":
                        menuTambah(sc);
                        break;
                    case "3":
                        menuEdit(sc);
                        break;
                    case "4":
                        menuHapus(sc);
                        break;
                    case "5":
                        lagi = false;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } catch (BarangTidakTersediaException e) {
                System.out.println("Gagal: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }

    /**
     * Mencetak tabel barang ke layar.
     *
     * @param daftar barang yang ditampilkan
     */
    public static void tampilkan(ArrayList<Barang> daftar) {
        if (daftar.isEmpty()) {
            System.out.println("(belum ada barang)");
            return;
        }
        System.out.printf("%-6s %-22s %-16s %12s  %-10s%n", "ID", "Nama", "Kategori", "Tarif/hari", "Status");
        for (Barang b : daftar) {
            System.out.printf("%-6s %-22s %-16s %12s  %-10s%n", b.getId(), b.getNama(), b.getKategori(),
                    String.format("%,.0f", b.getTarifHarian()), b.isTersedia() ? "Tersedia" : "Disewa");
        }
    }

    private void menuTambah(Scanner sc) {
        System.out.println("Kategori: 1. Sepeda Listrik  2. Skuter Listrik  3. Skuter Premium");
        System.out.print("Pilih kategori: ");
        String kat = sc.nextLine().trim();
        if (!kat.equals("1") && !kat.equals("2") && !kat.equals("3")) {
            System.out.println("Kategori tidak valid.");
            return;
        }
        System.out.print("ID barang: ");
        String id = sc.nextLine().trim();
        if (id.isEmpty() || adaId(id)) {
            System.out.println("ID kosong atau sudah dipakai.");
            return;
        }
        System.out.print("Nama barang: ");
        String nama = sc.nextLine().trim();
        System.out.print("Tarif per hari: ");
        double tarif = Double.parseDouble(sc.nextLine().trim());
        if (nama.isEmpty() || tarif <= 0) {
            System.out.println("Nama tidak boleh kosong dan tarif harus lebih dari 0.");
            return;
        }
        if (kat.equals("1")) {
            tambahBarang(new SepedaListrik(id, nama, tarif));
        } else if (kat.equals("2")) {
            tambahBarang(new SkuterListrik(id, nama, tarif));
        } else {
            tambahBarang(new SkuterPremium(id, nama, tarif));
        }
        System.out.println("Barang ditambahkan.");
    }

    private void menuEdit(Scanner sc) throws BarangTidakTersediaException {
        System.out.print("ID barang yang diedit: ");
        Barang b = cariBarang(sc.nextLine().trim());
        System.out.print("Nama baru (" + b.getNama() + "): ");
        String nama = sc.nextLine().trim();
        System.out.print("Tarif baru (" + String.format("%,.0f", b.getTarifHarian()) + "): ");
        double tarif = Double.parseDouble(sc.nextLine().trim());
        if (nama.isEmpty() || tarif <= 0) {
            System.out.println("Nama tidak boleh kosong dan tarif harus lebih dari 0.");
            return;
        }
        editBarang(b.getId(), nama, tarif);
        System.out.println("Barang diperbarui.");
    }

    private void menuHapus(Scanner sc) throws BarangTidakTersediaException {
        System.out.print("ID barang yang dihapus: ");
        hapusBarang(sc.nextLine().trim());
        System.out.println("Barang dihapus.");
    }
}
