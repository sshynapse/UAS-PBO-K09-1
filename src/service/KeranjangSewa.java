package service;

import exception.BarangTidakTersediaException;
import java.util.ArrayList;
import model.Barang;

/**
 * Keranjang sewa milik penyewa. Menyimpan barang yang akan disewa.
 */
public class KeranjangSewa {
    private ArrayList<Barang> barang = new ArrayList<>();

    /**
     * Menambahkan barang ke keranjang.
     *
     * @param b barang yang ditambahkan
     * @throws BarangTidakTersediaException jika barang sedang disewa atau sudah ada di keranjang
     */
    public void tambahBarang(Barang b) throws BarangTidakTersediaException {
        if (!b.isTersedia()) {
            throw new BarangTidakTersediaException("Barang " + b.getNama() + " (" + b.getId() + ") sedang disewa");
        }
        for (Barang x : barang) {
            if (x.getId().equals(b.getId())) {
                throw new BarangTidakTersediaException("Barang " + b.getNama() + " sudah ada di keranjang");
            }
        }
        barang.add(b);
    }

    /**
     * Menghapus barang dari keranjang berdasarkan ID.
     *
     * @param id ID barang
     * @throws BarangTidakTersediaException jika ID tidak ada di keranjang
     */
    public void hapusBarang(String id) throws BarangTidakTersediaException {
        for (int i = 0; i < barang.size(); i++) {
            if (barang.get(i).getId().equalsIgnoreCase(id)) {
                barang.remove(i);
                return;
            }
        }
        throw new BarangTidakTersediaException("Barang dengan ID " + id + " tidak ada di keranjang");
    }

    /**
     * @return daftar barang di keranjang
     */
    public ArrayList<Barang> getIsi() {
        return barang;
    }

    /**
     * Mengosongkan keranjang.
     */
    public void kosongkan() {
        barang.clear();
    }

    /**
     * @return true jika keranjang kosong
     */
    public boolean isKosong() {
        return barang.isEmpty();
    }
}
