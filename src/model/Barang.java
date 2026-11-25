package model;

/**
 * Class abstrak yang mewakili barang yang disewakan.
 * Tiap kategori menghitung biaya sewanya sendiri (polymorphism).
 */
public abstract class Barang {
    private String id;
    private String nama;
    private double tarifHarian;
    private boolean tersedia;

    /**
     * Membuat barang baru yang berstatus tersedia.
     *
     * @param id          ID unik barang
     * @param nama        nama barang
     * @param tarifHarian tarif sewa per hari dalam rupiah
     */
    public Barang(String id, String nama, double tarifHarian) {
        this.id = id;
        this.nama = nama;
        this.tarifHarian = tarifHarian;
        this.tersedia = true;
    }

    /** @return ID barang */
    public String getId() {
        return id;
    }

    /** @return nama barang */
    public String getNama() {
        return nama;
    }

    /** @param nama nama baru barang */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /** @return tarif sewa per hari */
    public double getTarifHarian() {
        return tarifHarian;
    }

    /** @param tarifHarian tarif baru per hari */
    public void setTarifHarian(double tarifHarian) {
        this.tarifHarian = tarifHarian;
    }

    /** @return true jika barang tidak sedang disewa */
    public boolean isTersedia() {
        return tersedia;
    }

    /** @param tersedia status ketersediaan baru */
    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }

    /**
     * Menghitung biaya sewa sesuai kategori barang.
     *
     * @param hari lama sewa dalam hari
     * @return biaya sewa dalam rupiah
     */
    public abstract double hitungBiaya(int hari);

    /** @return nama kategori barang */
    public abstract String getKategori();
}
