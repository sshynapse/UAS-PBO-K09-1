package service;

import exception.LoginGagalException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Admin;
import model.Akun;
import model.SepedaListrik;
import model.SkuterListrik;
import model.SkuterPremium;
import model.Penyewa;
import model.Transaksi;

/**
 * Penyimpan data bersama yang dipakai Admin dan Penyewa:
 * akun, daftar barang, dan daftar transaksi.
 */
public class DataSistem {
    private HashMap<String, Akun> akun = new HashMap<>();
    private DaftarBarang daftarBarang = new DaftarBarang();
    private ArrayList<Transaksi> daftarTransaksi = new ArrayList<>();
    private int counterTransaksi = 0;

    /**
     * Membuat data awal: 1 admin, 2 penyewa, dan 5 barang contoh.
     */
    public DataSistem() {
        akun.put("admin", new Admin("A01", "admin", "admin123"));
        akun.put("budi", new Penyewa("P01", "budi", "budi123"));
        akun.put("sari", new Penyewa("P02", "sari", "sari123"));

        daftarBarang.tambahBarang(new SepedaListrik("B001", "Sepeda Listrik A", 50000));
        daftarBarang.tambahBarang(new SepedaListrik("B002", "Sepeda Listrik B", 55000));
        daftarBarang.tambahBarang(new SkuterListrik("B003", "Skuter Listrik A", 60000));
        daftarBarang.tambahBarang(new SkuterListrik("B004", "Skuter Listrik B", 65000));
        daftarBarang.tambahBarang(new SkuterPremium("B005", "Skuter Premium A", 80000));
    }

    /**
     * Memeriksa login pengguna.
     *
     * @param username username yang diinput
     * @param password password yang diinput
     * @return akun yang berhasil login
     * @throws LoginGagalException jika username atau password salah
     */
    public Akun login(String username, String password) throws LoginGagalException {
        Akun a = akun.get(username);
        if (a == null || !a.login(username, password)) {
            throw new LoginGagalException("Username atau password salah");
        }
        return a;
    }

    /**
     * @return daftar barang sistem
     */
    public DaftarBarang getDaftarBarang() {
        return daftarBarang;
    }

    /**
     * @return semua transaksi
     */
    public ArrayList<Transaksi> getDaftarTransaksi() {
        return daftarTransaksi;
    }

    /**
     * Menyimpan transaksi baru.
     *
     * @param t transaksi baru
     */
    public void tambahTransaksi(Transaksi t) {
        daftarTransaksi.add(t);
    }

    /**
     * Mengambil transaksi berdasarkan status.
     *
     * @param status MENUNGGU, AKTIF, atau SELESAI
     * @return daftar transaksi dengan status tersebut
     */
    public ArrayList<Transaksi> getTransaksiByStatus(String status) {
        ArrayList<Transaksi> hasil = new ArrayList<>();
        for (Transaksi t : daftarTransaksi) {
            if (t.getStatus().equals(status)) {
                hasil.add(t);
            }
        }
        return hasil;
    }

    /**
     * Mencari transaksi berdasarkan ID dan status.
     *
     * @param id     ID transaksi
     * @param status status yang dicari
     * @return transaksi yang cocok, atau null jika tidak ada
     */
    public Transaksi cariTransaksi(String id, String status) {
        for (Transaksi t : getTransaksiByStatus(status)) {
            if (t.getId().equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Membuat ID transaksi baru, contoh TRX001.
     *
     * @return ID transaksi
     */
    public String buatIdTransaksi() {
        counterTransaksi++;
        return String.format("TRX%03d", counterTransaksi);
    }
}
