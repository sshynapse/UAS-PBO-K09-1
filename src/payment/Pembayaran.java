package payment;

/**
 * Interface metode pembayaran. Diimplementasikan oleh
 * {@code TransferBank}, {@code QRIS}, dan {@code Tunai}
 */
public interface Pembayaran {

    /**
     * Memproses pembayaran sesuai metode yang dipilih
     *
     * @param jumlah nominal yang dibayar dalam rupiah
     */
    void prosesPembayaran(double jumlah);

    /**
     * @return nama metode pembayaran
     */
    String getNamaMetode();
}