package model;

import exception.BarangTidakTersediaException;
import java.util.ArrayList;
import java.util.Scanner;
import payment.Pembayaran;
import payment.QRIS;
import payment.TransferBank;
import payment.Tunai;
import service.DaftarBarang;
import service.DataSistem;
import service.KeranjangSewa;

/**
 * Akun penyewa. Dapat melihat barang, mengisi keranjang sewa,
 * membuat transaksi, dan melihat riwayat sewa.
 */
public class Penyewa extends Akun {
    private KeranjangSewa keranjang;
    private ArrayList<Transaksi> riwayat;

    /**
     * Membuat akun penyewa baru.
     *
     * @param id       ID akun
     * @param username username login
     * @param password password login
     */
    public Penyewa(String id, String username, String password) {
        super(id, username, password);
        this.keranjang = new KeranjangSewa();
        this.riwayat = new ArrayList<>();
    }

    /**
     * @return keranjang sewa milik penyewa
     */
    public KeranjangSewa getKeranjang() {
        return keranjang;
    }

    /**
     * @return daftar transaksi yang sudah diterima admin
     */
    public ArrayList<Transaksi> getRiwayat() {
        return riwayat;
    }

    /**
     * Menambahkan transaksi ke riwayat penyewa.
     *
     * @param t transaksi yang diterima admin
     */
    public void tambahRiwayat(Transaksi t) {
        riwayat.add(t);
    }

    /**
     * Menampilkan barang yang tersedia untuk disewa.
     *
     * @param daftar daftar barang sistem
     */
    public void lihatBarang(DaftarBarang daftar) {
        System.out.println("\n--- Barang Tersedia ---");
        DaftarBarang.tampilkan(daftar.getBarangTersedia());
    }

    /**
     * Membuat transaksi dari isi keranjang.
     *
     * @param lamaSewa    lama sewa dalam hari
     * @param pembayaran  metode pembayaran yang dipilih
     * @param idTransaksi ID untuk transaksi baru
     * @return transaksi yang dibuat (status MENUNGGU)
     */
    public Transaksi buatTransaksi(int lamaSewa, Pembayaran pembayaran, String idTransaksi) {
        return new Transaksi(idTransaksi, this, new ArrayList<>(keranjang.getIsi()), lamaSewa, pembayaran);
    }

    /**
     * Menampilkan riwayat sewa penyewa (transaksi yang sudah diterima admin).
     */
    public void lihatRiwayat() {
        System.out.println("\n--- Riwayat Sewa ---");
        if (riwayat.isEmpty()) {
            System.out.println("(belum ada riwayat)");
            return;
        }
        for (Transaksi t : riwayat) {
            System.out.printf("%s | %s | jatuh tempo %s | total Rp%,.0f | denda Rp%,.0f%n",
                    t.getId(), t.getStatus(), t.getTanggalJatuhTempo(), t.hitungTotal(), t.hitungDenda());
        }
    }

    /**
     * Menampilkan menu utama penyewa (CLI).
     *
     * @param data data sistem bersama
     * @param sc   scanner untuk input
     */
    public void tampilMenu(DataSistem data, Scanner sc) {
        boolean lagi = true;
        while (lagi) {
            System.out.println("\n=== MENU PENYEWA (" + getUsername() + ") ===");
            System.out.println("1. Lihat Barang Tersedia");
            System.out.println("2. Tambah Barang ke Keranjang");
            System.out.println("3. Lihat / Hapus Isi Keranjang");
            System.out.println("4. Checkout");
            System.out.println("5. Riwayat Sewa");
            System.out.println("6. Logout");
            System.out.print("Pilih menu: ");
            String pilih = sc.nextLine().trim();
            try {
                switch (pilih) {
                    case "1":
                        lihatBarang(data.getDaftarBarang());
                        break;
                    case "2":
                        System.out.print("ID barang: ");
                        Barang b = data.getDaftarBarang().cariBarang(sc.nextLine().trim());
                        keranjang.tambahBarang(b);
                        System.out.println(b.getNama() + " masuk ke keranjang.");
                        break;
                    case "3":
                        menuKeranjang(sc);
                        break;
                    case "4":
                        menuCheckout(data, sc);
                        break;
                    case "5":
                        lihatRiwayat();
                        break;
                    case "6":
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

    private void menuKeranjang(Scanner sc) throws BarangTidakTersediaException {
        System.out.println("\n--- Isi Keranjang ---");
        if (keranjang.isKosong()) {
            System.out.println("(keranjang kosong)");
            return;
        }
        DaftarBarang.tampilkan(keranjang.getIsi());
        System.out.print("Ketik ID untuk menghapus (kosong = kembali): ");
        String id = sc.nextLine().trim();
        if (!id.isEmpty()) {
            keranjang.hapusBarang(id);
            System.out.println("Barang dihapus dari keranjang.");
        }
    }

    private void menuCheckout(DataSistem data, Scanner sc) {
        if (keranjang.isKosong()) {
            System.out.println("Keranjang masih kosong.");
            return;
        }
        System.out.print("Lama sewa (hari): ");
        int lama = Integer.parseInt(sc.nextLine().trim());
        if (lama <= 0) {
            System.out.println("Lama sewa harus lebih dari 0 hari.");
            return;
        }
        System.out.println("Metode pembayaran: 1. Transfer Bank  2. QRIS  3. Tunai");
        System.out.print("Pilih metode: ");
        Pembayaran p;
        switch (sc.nextLine().trim()) {
            case "1":
                p = new TransferBank();
                break;
            case "2":
                p = new QRIS();
                break;
            case "3":
                p = new Tunai();
                break;
            default:
                System.out.println("Metode tidak valid.");
                return;
        }
        Transaksi t = buatTransaksi(lama, p, data.buatIdTransaksi());
        data.tambahTransaksi(t);
        keranjang.kosongkan();
        System.out.printf("Transaksi %s dibuat. Total Rp%,.0f (%s).%n", t.getId(), t.hitungTotal(), p.getNamaMetode());
        p.prosesPembayaran(t.hitungTotal());
        System.out.println("Status MENUNGGU persetujuan admin.");
    }
}
