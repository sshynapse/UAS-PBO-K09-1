package model;

import java.time.LocalDate;
import java.util.ArrayList;
import payment.Pembayaran;

/**
 * Transaksi sewa yang dibuat oleh penyewa dan diproses oleh admin.
 * (Kerangka: isi method dilengkapi pada commit berikutnya.)
 */
public class Transaksi {

    /** Status transaksi yang menunggu persetujuan admin. */
    public static final String MENUNGGU = "MENUNGGU";
    /** Status transaksi yang sudah diterima admin (barang sedang disewa). */
    public static final String AKTIF = "AKTIF";
    /** Status transaksi yang barangnya sudah dikembalikan. */
    public static final String SELESAI = "SELESAI";

    private String id;
    private Penyewa penyewa;
    private ArrayList<Barang> barang;
    private int lamaSewa;
    private LocalDate tanggalSewa;
    private LocalDate tanggalJatuhTempo;
    private LocalDate tanggalKembali;
    private String status;
    private Pembayaran pembayaran;

    /**
     * Membuat transaksi baru.
     *
     * @param id         ID transaksi
     * @param penyewa    penyewa yang membuat transaksi
     * @param barang     daftar barang yang disewa
     * @param lamaSewa   lama sewa dalam hari
     * @param pembayaran metode pembayaran yang dipilih
     */
    public Transaksi(String id, Penyewa penyewa, ArrayList<Barang> barang,
                     int lamaSewa, Pembayaran pembayaran) {
        this.id = id;
        this.penyewa = penyewa;
        this.barang = barang;
        this.lamaSewa = lamaSewa;
        this.pembayaran = pembayaran;
    }

    /**
     * Menghitung total biaya sewa seluruh barang.
     *
     * @return total biaya dalam rupiah
     */
    public double hitungTotal() {
        return 0;
    }

    /**
     * Menghitung denda keterlambatan pengembalian.
     *
     * @return total denda dalam rupiah, 0 jika tidak terlambat
     */
    public double hitungDenda() {
        return 0;
    }

    /**
     * Mengubah status transaksi.
     *
     * @param status MENUNGGU, AKTIF, atau SELESAI
     */
    public void ubahStatus(String status) {
    }
}