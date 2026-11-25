package payment;

/**
 * Metode pembayaran secara tunai langsung ke admin.
 */
public class Tunai implements Pembayaran {

    /**
     * Menampilkan instruksi pembayaran tunai.
     *
     * @param jumlah nominal yang harus dibayar dalam rupiah
     */
    @Override
    public void prosesPembayaran(double jumlah) {
        System.out.println("Bayar tunai Rp" + String.format("%,.0f", jumlah) + " ke admin");
    }

    /**
     * @return nama metode pembayaran
     */
    @Override
    public String getNamaMetode() {
        return "Tunai";
    }
}
