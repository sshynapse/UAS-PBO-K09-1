package model;

import exception.BarangTidakTersediaException;
import java.time.LocalDate;
import java.util.Scanner;
import service.DaftarBarang;
import service.DataSistem;

/**
 * Akun admin. Mengelola barang, menerima transaksi sewa,
 * dan mengonfirmasi pengembalian barang beserta denda
 */
public class Admin extends Akun {

    /**
     * Membuat akun admin baru
     *
     * @param id       ID akun
     * @param username username login
     * @param password password login
     */
    public Admin(String id, String username, String password) {
        super(id, username, password);
    }

    /**
     * Membuka menu kelola barang (tambah, edit, hapus)
     *
     * @param daftar daftar barang sistem
     * @param sc     scanner untuk input
     */
    public void kelolaBarang(DaftarBarang daftar, Scanner sc) {
    }

    /**
     * Menerima transaksi: barang menjadi disewa, status AKTIF, invoice dicetak
     *
     * @param t transaksi berstatus MENUNGGU
     * @throws BarangTidakTersediaException jika ada barang yang sudah disewa
     */
    public void terimaTransaksi(Transaksi t) throws BarangTidakTersediaException {
    }

    /**
     * Mengonfirmasi pengembalian barang dan menghitung denda jika terlambat
     *
     * @param t              transaksi berstatus AKTIF
     * @param tanggalKembali tanggal barang dikembalikan
     */
    public void konfirmasiKembali(Transaksi t, LocalDate tanggalKembali) {
    }

    /**
     * Menampilkan menu utama admin
     *
     * @param data data sistem bersama
     * @param sc   scanner untuk input
     */
    public void tampilMenu(DataSistem data, Scanner sc) {
    }
}