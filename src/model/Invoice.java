package model;

/* Invoice yang dicetak ketika transaksi diterima admin dan ketika barang dikembalikan */
public class Invoice {
    private Transaksi transaksi;

    /**
     * Membuat invoice untuk sebuah transaksi
     *
     * @param t transaksi yang akan dicetak invoice-nya
     */
    public Invoice(Transaksi t) {
        this.transaksi = t;
    }

    /* Mencetak rincian invoice ke layar */
    public void cetakInvoice() {
        System.out.println("=== INVOICE ===");
    }
}