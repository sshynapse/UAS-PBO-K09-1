package model;

/**
 * Sepeda listrik. Biaya = tarif harian x lama sewa.
 */
public class SepedaListrik extends Barang {

    /**
     * Membuat sepeda listrik.
     *
     * @param id          ID barang
     * @param nama        nama barang
     * @param tarifHarian tarif per hari
     */
    public SepedaListrik(String id, String nama, double tarifHarian) {
        super(id, nama, tarifHarian);
    }

    @Override
    public double hitungBiaya(int hari) {
        return getTarifHarian() * hari;
    }

    @Override
    public String getKategori() {
        return "Sepeda Listrik";
    }
}
