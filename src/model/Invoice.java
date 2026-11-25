package model;

/**
 * Invoice yang dicetak ketika transaksi diterima admin
 * dan ketika barang dikembalikan.
 */
public class Invoice {
    private Transaksi transaksi;

    /**
     * Membuat invoice untuk sebuah transaksi.
     *
     * @param t transaksi yang akan dicetak invoice-nya
     */
    public Invoice(Transaksi t) {
        this.transaksi = t;
    }

    /**
     * Menyusun teks invoice: data transaksi, daftar barang beserta biayanya,
     * total sewa, denda, total akhir, dan metode pembayaran.
     *
     * @return teks invoice
     */
    public String buatTeks() {
        double total = transaksi.hitungTotal();
        double denda = transaksi.hitungDenda();
        String garis = "==================================================\n";
        String garisTipis = "--------------------------------------------------\n";
        StringBuilder sb = new StringBuilder();
        sb.append(garis);
        sb.append("                   INVOICE SEWA\n");
        sb.append(garis);
        sb.append("ID Transaksi : ").append(transaksi.getId()).append("\n");
        sb.append("Penyewa      : ").append(transaksi.getPenyewa().getUsername()).append("\n");
        sb.append("Tanggal Sewa : ").append(transaksi.getTanggalSewa()).append("\n");
        sb.append("Jatuh Tempo  : ").append(transaksi.getTanggalJatuhTempo()).append("\n");
        sb.append("Lama Sewa    : ").append(transaksi.getLamaSewa()).append(" hari\n");
        if (transaksi.getTanggalKembali() != null) {
            sb.append("Tgl Kembali  : ").append(transaksi.getTanggalKembali()).append("\n");
        }
        sb.append("Status       : ").append(transaksi.getStatus()).append("\n");
        sb.append(garisTipis);
        sb.append("Barang:\n");
        int no = 1;
        for (Barang b : transaksi.getBarang()) {
            sb.append(no).append(". ").append(b.getNama()).append(" (").append(b.getKategori())
              .append(") - ").append(rupiah(b.hitungBiaya(transaksi.getLamaSewa()))).append("\n");
            no++;
        }
        sb.append(garisTipis);
        sb.append("Total Sewa   : ").append(rupiah(total)).append("\n");
        sb.append("Denda        : ").append(rupiah(denda)).append("\n");
        sb.append("TOTAL AKHIR  : ").append(rupiah(total + denda)).append("\n");
        sb.append("Metode Bayar : ").append(transaksi.getPembayaran().getNamaMetode()).append("\n");
        sb.append(garis);
        return sb.toString();
    }

    /**
     * Mencetak invoice ke layar (CLI).
     */
    public void cetakInvoice() {
        System.out.print(buatTeks());
    }

    private String rupiah(double jumlah) {
        return "Rp" + String.format("%,.0f", jumlah);
    }
}
