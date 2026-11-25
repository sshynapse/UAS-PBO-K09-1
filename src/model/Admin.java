package model;

import exception.BarangTidakTersediaException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import service.DaftarBarang;
import service.DataSistem;

/**
 * Akun admin. Mengelola barang, menerima transaksi sewa,
 * dan mengonfirmasi pengembalian barang beserta denda.
 */
public class Admin extends Akun {

    /**
     * Membuat akun admin baru.
     *
     * @param id       ID akun
     * @param username username login
     * @param password password login
     */
    public Admin(String id, String username, String password) {
        super(id, username, password);
    }

    /**
     * Membuka menu kelola barang (tambah, edit, hapus).
     *
     * @param daftar daftar barang sistem
     * @param sc     scanner untuk input
     */
    public void kelolaBarang(DaftarBarang daftar, Scanner sc) {
        daftar.menuKelola(sc);
    }

    /**
     * Menerima transaksi: semua barang menjadi disewa, status menjadi AKTIF,
     * transaksi masuk riwayat penyewa, dan invoice dicetak.
     * Jika ada satu barang saja yang sedang disewa, transaksi ditolak
     * dan tidak ada barang yang berubah status.
     *
     * @param t transaksi berstatus MENUNGGU
     * @throws BarangTidakTersediaException jika ada barang yang sudah disewa
     */
    public void terimaTransaksi(Transaksi t) throws BarangTidakTersediaException {
        for (Barang b : t.getBarang()) {
            if (!b.isTersedia()) {
                throw new BarangTidakTersediaException("Barang " + b.getNama()
                        + " (" + b.getId() + ") sedang disewa");
            }
        }
        for (Barang b : t.getBarang()) {
            b.setTersedia(false);
        }
        t.ubahStatus(Transaksi.AKTIF);
        t.getPenyewa().tambahRiwayat(t);
        new Invoice(t).cetakInvoice();
    }

    /**
     * Mengonfirmasi pengembalian barang, menghitung denda jika terlambat,
     * mengembalikan status barang menjadi tersedia, dan mencetak invoice akhir.
     *
     * @param t              transaksi berstatus AKTIF
     * @param tanggalKembali tanggal barang dikembalikan
     */
    public void konfirmasiKembali(Transaksi t, LocalDate tanggalKembali) {
        t.setTanggalKembali(tanggalKembali);
        for (Barang b : t.getBarang()) {
            b.setTersedia(true);
        }
        t.ubahStatus(Transaksi.SELESAI);
        new Invoice(t).cetakInvoice();
    }

    /**
     * Menampilkan menu utama admin (CLI).
     *
     * @param data data sistem bersama
     * @param sc   scanner untuk input
     */
    public void tampilMenu(DataSistem data, Scanner sc) {
        boolean lagi = true;
        while (lagi) {
            System.out.println("\n=== MENU ADMIN ===");
            System.out.println("1. Kelola Barang");
            System.out.println("2. Lihat & Terima Transaksi (MENUNGGU)");
            System.out.println("3. Konfirmasi Pengembalian (AKTIF)");
            System.out.println("4. Logout");
            System.out.print("Pilih menu: ");
            String pilih = sc.nextLine().trim();
            switch (pilih) {
                case "1":
                    kelolaBarang(data.getDaftarBarang(), sc);
                    break;
                case "2":
                    menuTerima(data, sc);
                    break;
                case "3":
                    menuKembali(data, sc);
                    break;
                case "4":
                    lagi = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private void menuTerima(DataSistem data, Scanner sc) {
        ArrayList<Transaksi> menunggu = data.getTransaksiByStatus(Transaksi.MENUNGGU);
        System.out.println("\n--- Transaksi Menunggu ---");
        if (menunggu.isEmpty()) {
            System.out.println("(tidak ada transaksi menunggu)");
            return;
        }
        for (Transaksi t : menunggu) {
            System.out.printf("%s | %s | %s | %d hari | Rp%,.0f%n", t.getId(), t.getPenyewa().getUsername(),
                    ringkasBarang(t), t.getLamaSewa(), t.hitungTotal());
        }
        System.out.print("ID transaksi yang diterima (kosong = kembali): ");
        String id = sc.nextLine().trim();
        if (id.isEmpty()) {
            return;
        }
        Transaksi t = data.cariTransaksi(id, Transaksi.MENUNGGU);
        if (t == null) {
            System.out.println("Transaksi tidak ditemukan.");
            return;
        }
        try {
            terimaTransaksi(t);
        } catch (BarangTidakTersediaException e) {
            System.out.println("Transaksi ditolak: " + e.getMessage());
        }
    }

    private void menuKembali(DataSistem data, Scanner sc) {
        ArrayList<Transaksi> aktif = data.getTransaksiByStatus(Transaksi.AKTIF);
        System.out.println("\n--- Transaksi Aktif ---");
        if (aktif.isEmpty()) {
            System.out.println("(tidak ada transaksi aktif)");
            return;
        }
        for (Transaksi t : aktif) {
            System.out.printf("%s | %s | %s | jatuh tempo %s%n", t.getId(), t.getPenyewa().getUsername(),
                    ringkasBarang(t), t.getTanggalJatuhTempo());
        }
        System.out.print("ID transaksi yang dikembalikan (kosong = kembali): ");
        String id = sc.nextLine().trim();
        if (id.isEmpty()) {
            return;
        }
        Transaksi t = data.cariTransaksi(id, Transaksi.AKTIF);
        if (t == null) {
            System.out.println("Transaksi tidak ditemukan.");
            return;
        }
        System.out.print("Tanggal kembali (yyyy-MM-dd, kosong = hari ini): ");
        String teks = sc.nextLine().trim();
        try {
            LocalDate tgl = teks.isEmpty() ? LocalDate.now() : LocalDate.parse(teks);
            if (tgl.isBefore(t.getTanggalSewa())) {
                System.out.println("Tanggal kembali tidak boleh sebelum tanggal sewa.");
                return;
            }
            konfirmasiKembali(t, tgl);
        } catch (DateTimeParseException e) {
            System.out.println("Format tanggal salah. Gunakan yyyy-MM-dd, contoh 2026-11-25.");
        }
    }

    private String ringkasBarang(Transaksi t) {
        StringBuilder sb = new StringBuilder();
        for (Barang b : t.getBarang()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(b.getNama());
        }
        return sb.toString();
    }
}
