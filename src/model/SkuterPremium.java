package model;

/**
 * Skuter premium. Biaya = (tarif harian + biaya helm dan baterai cadangan) x lama sewa.
 */
public class SkuterPremium extends Barang {
    /** Biaya helm dan baterai cadangan per hari. */
    public static final double BIAYA_PERLENGKAPAN = 10000;

    /**
     * Membuat skuter premium.
     *
     * @param id          ID barang
     * @param nama        nama barang
     * @param tarifHarian tarif per hari
     */
    public SkuterPremium(String id, String nama, double tarifHarian) {
        super(id, nama, tarifHarian);
    }

    @Override
    public double hitungBiaya(int hari) {
        return (getTarifHarian() + BIAYA_PERLENGKAPAN) * hari;
    }

    @Override
    public String getKategori() {
        return "Skuter Premium";
    }
}
