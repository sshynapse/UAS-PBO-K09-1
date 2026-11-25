package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import payment.Pembayaran;

/**
 * Transaksi sewa yang dibuat oleh penyewa dan diproses oleh admin.
 */
public class Transaksi {

    /** Denda keterlambatan per hari per barang (rupiah). */
    public static final double DENDA_PER_HARI = 20000;

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
     * Membuat transaksi baru. Tanggal sewa diisi hari ini,
     * tanggal jatuh tempo dihitung dari lama sewa, dan status awal MENUNGGU.
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
        this.tanggalSewa = LocalDate.now();
        this.tanggalJatuhTempo = tanggalSewa.plusDays(lamaSewa);
        this.status = MENUNGGU;
    }

    /**
     * @return ID transaksi
     */
    public String getId() {
        return id;
    }

    /**
     * @return penyewa yang membuat transaksi
     */
    public Penyewa getPenyewa() {
        return penyewa;
    }

    /**
     * @return daftar barang yang disewa
     */
    public ArrayList<Barang> getBarang() {
        return barang;
    }

    /**
     * @return lama sewa dalam hari
     */
    public int getLamaSewa() {
        return lamaSewa;
    }

    /**
     * @return tanggal mulai sewa
     */
    public LocalDate getTanggalSewa() {
        return tanggalSewa;
    }

    /**
     * @return tanggal jatuh tempo pengembalian
     */
    public LocalDate getTanggalJatuhTempo() {
        return tanggalJatuhTempo;
    }

    /**
     * @return tanggal barang dikembalikan, null jika belum dikembalikan
     */
    public LocalDate getTanggalKembali() {
        return tanggalKembali;
    }

    /**
     * Mengisi tanggal barang dikembalikan.
     *
     * @param tanggalKembali tanggal pengembalian
     */
    public void setTanggalKembali(LocalDate tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    /**
     * @return status transaksi saat ini
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return metode pembayaran yang dipilih
     */
    public Pembayaran getPembayaran() {
        return pembayaran;
    }

    /**
     * Menghitung total biaya sewa seluruh barang.
     * Tiap barang menghitung biayanya sendiri sesuai kategorinya.
     *
     * @return total biaya dalam rupiah
     */
    public double hitungTotal() {
        double total = 0;
        for (Barang b : barang) {
            total += b.hitungBiaya(lamaSewa);
        }
        return total;
    }

    /**
     * Menghitung denda keterlambatan pengembalian.
     * Denda = hari telat x Rp20.000 x jumlah barang.
     *
     * @return total denda dalam rupiah, 0 jika belum dikembalikan atau tidak terlambat
     */
    public double hitungDenda() {
        if (tanggalKembali == null) {
            return 0;
        }
        long hariTelat = ChronoUnit.DAYS.between(tanggalJatuhTempo, tanggalKembali);
        if (hariTelat <= 0) {
            return 0;
        }
        return hariTelat * DENDA_PER_HARI * barang.size();
    }

    /**
     * Mengubah status transaksi.
     *
     * @param status MENUNGGU, AKTIF, atau SELESAI
     */
    public void ubahStatus(String status) {
        this.status = status;
    }
}
