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

    /* Mencetak rincian invoice ke layar: data transaksi, daftar barang
        beserta biayanya, total sewa, denda, total akhir, dan metode pembayaran */

    public void cetakInvoice() {
        double total = transaksi.hitungTotal();
        double denda = transaksi.hitungDenda();

        System.out.println("==================================================");
        System.out.println("                   INVOICE SEWA");
        System.out.println("==================================================");
        System.out.println("ID Transaksi : " + transaksi.getId());
        System.out.println("Penyewa      : " + transaksi.getPenyewa().getUsername());
        System.out.println("Tanggal Sewa : " + transaksi.getTanggalSewa());
        System.out.println("Jatuh Tempo  : " + transaksi.getTanggalJatuhTempo());
        System.out.println("Lama Sewa    : " + transaksi.getLamaSewa() + " hari");
        if (transaksi.getTanggalKembali() != null) {
            System.out.println("Tgl Kembali  : " + transaksi.getTanggalKembali());
        }
        System.out.println("Status       : " + transaksi.getStatus());
        System.out.println("--------------------------------------------------");
        System.out.println("Barang:");
        int no = 1;
        for (Barang b : transaksi.getBarang()) {
            System.out.println(no + ". " + b.getNama() + " (" + b.getKategori() + ") - "
                    + rupiah(b.hitungBiaya(transaksi.getLamaSewa())));
            no++;
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Total Sewa   : " + rupiah(total));
        System.out.println("Denda        : " + rupiah(denda));
        System.out.println("TOTAL AKHIR  : " + rupiah(total + denda));
        System.out.println("Metode Bayar : " + transaksi.getPembayaran().getNamaMetode());
        System.out.println("==================================================");
    }

    /**
     * Memformat angka menjadi tulisan rupiah, contoh Rp150,000
     *
     * @param jumlah nominal dalam rupiah
     * @return teks rupiah
     */
    private String rupiah(double jumlah) {
        return "Rp" + String.format("%,.0f", jumlah);
    }
}