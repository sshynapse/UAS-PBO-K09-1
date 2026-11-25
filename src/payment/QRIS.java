package payment;

/**
 * Metode pembayaran melalui QRIS.
 */
public class QRIS implements Pembayaran {

    /**
     * Menampilkan instruksi pembayaran QRIS.
     *
     * @param jumlah nominal yang harus dibayar dalam rupiah
     */
    @Override
    public void prosesPembayaran(double jumlah) {
        System.out.println("Scan QR untuk membayar Rp" + String.format("%,.0f", jumlah));
    }

    /**
     * @return nama metode pembayaran
     */
    @Override
    public String getNamaMetode() {
        return "QRIS";
    }
}
