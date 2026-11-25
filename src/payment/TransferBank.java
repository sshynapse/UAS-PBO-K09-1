package payment;

/**
 * Metode pembayaran melalui transfer bank.
 */
public class TransferBank implements Pembayaran {

    /**
     * Menampilkan instruksi transfer bank.
     *
     * @param jumlah nominal yang harus ditransfer dalam rupiah
     */
    @Override
    public void prosesPembayaran(double jumlah) {
        System.out.println("Silakan transfer Rp" + String.format("%,.0f", jumlah)
                + " ke rekening 123456 a.n. Rental Sepeda Listrik");
    }

    /**
     * @return nama metode pembayaran
     */
    @Override
    public String getNamaMetode() {
        return "Transfer Bank";
    }
}
