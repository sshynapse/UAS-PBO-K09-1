package model;

/**
 * Skuter listrik. Biaya = tarif harian x lama sewa + asuransi 5%.
 */
public class SkuterListrik extends Barang {
    /** Persentase biaya asuransi. */
    public static final double ASURANSI = 0.05;

    /**
     * Membuat skuter listrik.
     *
     * @param id          ID barang
     * @param nama        nama barang
     * @param tarifHarian tarif per hari
     */
    public SkuterListrik(String id, String nama, double tarifHarian) {
        super(id, nama, tarifHarian);
    }

    @Override
    public double hitungBiaya(int hari) {
        double dasar = getTarifHarian() * hari;
        return dasar + dasar * ASURANSI;
    }

    @Override
    public String getKategori() {
        return "Skuter Listrik";
    }
}
