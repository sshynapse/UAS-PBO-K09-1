package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import payment.Pembayaran;
import payment.QRIS;
import payment.TransferBank;
import payment.Tunai;

/**
 * Fungsi bantu yang dipakai bersama oleh semua jendela GUI.
 */
public class Util {

    private Util() {
    }

    /**
     * Memformat angka menjadi tulisan rupiah.
     *
     * @param jumlah nominal dalam rupiah
     * @return teks seperti Rp150,000
     */
    public static String rupiah(double jumlah) {
        return "Rp" + String.format("%,.0f", jumlah);
    }

    /**
     * Membuat model tabel yang selnya tidak bisa diedit langsung.
     *
     * @param kolom nama-nama kolom
     * @return model tabel kosong
     */
    public static DefaultTableModel modelTabel(String... kolom) {
        return new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int baris, int kolomKe) {
                return false;
            }
        };
    }

    /**
     * Membuat objek metode pembayaran dari urutan pilihan di combo box.
     *
     * @param indeks 0 = Transfer Bank, 1 = QRIS, 2 = Tunai
     * @return metode pembayaran yang dipilih
     */
    public static Pembayaran buatPembayaran(int indeks) {
        if (indeks == 0) {
            return new TransferBank();
        } else if (indeks == 1) {
            return new QRIS();
        }
        return new Tunai();
    }

    /**
     * Menampilkan pesan informasi.
     *
     * @param induk  jendela induk
     * @param pesan  isi pesan
     */
    public static void info(Component induk, String pesan) {
        JOptionPane.showMessageDialog(induk, pesan, "Informasi", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Menampilkan pesan kesalahan.
     *
     * @param induk jendela induk
     * @param pesan isi pesan
     */
    public static void error(Component induk, String pesan) {
        JOptionPane.showMessageDialog(induk, pesan, "Gagal", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Menampilkan teks panjang (misalnya invoice) dengan huruf lebar tetap.
     *
     * @param induk jendela induk
     * @param judul judul dialog
     * @param teks  isi teks
     */
    public static void tampilTeks(Component induk, String judul, String teks) {
        JTextArea area = new JTextArea(teks);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane gulir = new JScrollPane(area);
        gulir.setPreferredSize(new Dimension(540, 340));
        JOptionPane.showMessageDialog(induk, gulir, judul, JOptionPane.INFORMATION_MESSAGE);
    }
}
