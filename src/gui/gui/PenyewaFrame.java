package gui;

import exception.BarangTidakTersediaException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import model.Barang;
import model.Invoice;
import model.Penyewa;
import model.Transaksi;
import payment.Pembayaran;
import service.DataSistem;

/**
 * Jendela utama penyewa: melihat barang, mengisi keranjang,
 * checkout, dan melihat riwayat sewa.
 */
public class PenyewaFrame extends JFrame {
    private final DataSistem data;
    private final Penyewa penyewa;

    private final DefaultTableModel modelBarang = Util.modelTabel("ID", "Nama", "Kategori", "Tarif/hari");
    private final DefaultTableModel modelKeranjang = Util.modelTabel("ID", "Nama", "Kategori", "Tarif/hari");
    private final DefaultTableModel modelRiwayat =
            Util.modelTabel("ID", "Tanggal sewa", "Jatuh tempo", "Status", "Total", "Denda");
    private final JTable tabelBarang = new JTable(modelBarang);
    private final JTable tabelKeranjang = new JTable(modelKeranjang);
    private final JTable tabelRiwayat = new JTable(modelRiwayat);
    private final JSpinner spLama = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
    private final JComboBox<String> cbMetode =
            new JComboBox<>(new String[] {"Transfer Bank", "QRIS", "Tunai"});
    private final JLabel lblTotal = new JLabel();
    private ArrayList<Barang> barangTampil = new ArrayList<>();

    /**
     * Membuat jendela penyewa.
     *
     * @param data    data sistem bersama
     * @param penyewa penyewa yang sedang login
     */
    public PenyewaFrame(DataSistem data, Penyewa penyewa) {
        this.data = data;
        this.penyewa = penyewa;
        setTitle("Penyewa - " + penyewa.getUsername());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tab = new JTabbedPane();
        tab.addTab("Barang tersedia", panelBarang());
        tab.addTab("Keranjang dan checkout", panelKeranjang());
        tab.addTab("Riwayat sewa", panelRiwayat());
        tab.addChangeListener(e -> refreshSemua());

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame(data).setVisible(true);
        });
        JPanel bawah = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bawah.add(btnLogout);

        add(tab, BorderLayout.CENTER);
        add(bawah, BorderLayout.SOUTH);
        Theme.apply(this);
        setSize(780, 500);
        setLocationRelativeTo(null);
        refreshSemua();
    }

    private JPanel panelBarang() {
        JButton btnTambah = new JButton("Tambah ke keranjang");
        btnTambah.addActionListener(e -> tambahKeKeranjang());
        JPanel aksi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        aksi.add(btnTambah);
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JScrollPane(tabelBarang), BorderLayout.CENTER);
        p.add(aksi, BorderLayout.SOUTH);
        return p;
    }

    private JPanel panelKeranjang() {
        JButton btnHapus = new JButton("Hapus dari keranjang");
        btnHapus.addActionListener(e -> hapusDariKeranjang());
        JButton btnCheckout = new JButton("Checkout");
        btnCheckout.addActionListener(e -> checkout());
        spLama.addChangeListener(e -> refreshTotal());

        JPanel aksi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        aksi.add(btnHapus);
        aksi.add(new JLabel("   Lama sewa (hari):"));
        aksi.add(spLama);
        aksi.add(new JLabel("Metode bayar:"));
        aksi.add(cbMetode);
        aksi.add(btnCheckout);

        JPanel bawah = new JPanel(new BorderLayout());
        bawah.add(aksi, BorderLayout.CENTER);
        bawah.add(lblTotal, BorderLayout.SOUTH);

        JPanel p = new JPanel(new BorderLayout());
        p.add(new JScrollPane(tabelKeranjang), BorderLayout.CENTER);
        p.add(bawah, BorderLayout.SOUTH);
        return p;
    }

    private JPanel panelRiwayat() {
        JButton btnInvoice = new JButton("Lihat invoice");
        btnInvoice.addActionListener(e -> lihatInvoice());
        JPanel aksi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        aksi.add(btnInvoice);
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JScrollPane(tabelRiwayat), BorderLayout.CENTER);
        p.add(aksi, BorderLayout.SOUTH);
        return p;
    }

    private void tambahKeKeranjang() {
        int baris = tabelBarang.getSelectedRow();
        if (baris < 0) {
            Util.error(this, "Pilih satu barang dulu.");
            return;
        }
        try {
            Barang b = barangTampil.get(baris);
            penyewa.getKeranjang().tambahBarang(b);
            Util.info(this, b.getNama() + " masuk ke keranjang.");
            refreshSemua();
        } catch (BarangTidakTersediaException ex) {
            Util.error(this, ex.getMessage());
        }
    }

    private void hapusDariKeranjang() {
        int baris = tabelKeranjang.getSelectedRow();
        if (baris < 0) {
            Util.error(this, "Pilih satu barang di keranjang dulu.");
            return;
        }
        try {
            penyewa.getKeranjang().hapusBarang(penyewa.getKeranjang().getIsi().get(baris).getId());
            refreshSemua();
        } catch (BarangTidakTersediaException ex) {
            Util.error(this, ex.getMessage());
        }
    }

    private void checkout() {
        if (penyewa.getKeranjang().isKosong()) {
            Util.error(this, "Keranjang masih kosong.");
            return;
        }
        int lama = (Integer) spLama.getValue();
        Pembayaran p = Util.buatPembayaran(cbMetode.getSelectedIndex());
        Transaksi t = penyewa.buatTransaksi(lama, p, data.buatIdTransaksi());
        data.tambahTransaksi(t);
        penyewa.getKeranjang().kosongkan();
        p.prosesPembayaran(t.hitungTotal());
        Util.info(this, "Transaksi " + t.getId() + " dibuat.\nMetode: " + p.getNamaMetode()
                + "\nTotal: " + Util.rupiah(t.hitungTotal())
                + "\nStatus: menunggu persetujuan admin.");
        refreshSemua();
    }

    private void lihatInvoice() {
        int baris = tabelRiwayat.getSelectedRow();
        if (baris < 0) {
            Util.error(this, "Pilih satu transaksi dulu.");
            return;
        }
        Transaksi t = penyewa.getRiwayat().get(baris);
        Util.tampilTeks(this, "Invoice " + t.getId(), new Invoice(t).buatTeks());
    }

    private void refreshSemua() {
        modelBarang.setRowCount(0);
        barangTampil = data.getDaftarBarang().getBarangTersedia();
        for (Barang b : barangTampil) {
            modelBarang.addRow(new Object[] {b.getId(), b.getNama(), b.getKategori(), Util.rupiah(b.getTarifHarian())});
        }
        modelKeranjang.setRowCount(0);
        for (Barang b : penyewa.getKeranjang().getIsi()) {
            modelKeranjang.addRow(new Object[] {b.getId(), b.getNama(), b.getKategori(), Util.rupiah(b.getTarifHarian())});
        }
        modelRiwayat.setRowCount(0);
        for (Transaksi t : penyewa.getRiwayat()) {
            modelRiwayat.addRow(new Object[] {t.getId(), t.getTanggalSewa(), t.getTanggalJatuhTempo(),
                t.getStatus(), Util.rupiah(t.hitungTotal()), Util.rupiah(t.hitungDenda())});
        }
        refreshTotal();
    }

    private void refreshTotal() {
        int lama = (Integer) spLama.getValue();
        double total = 0;
        for (Barang b : penyewa.getKeranjang().getIsi()) {
            total += b.hitungBiaya(lama);
        }
        lblTotal.setText("  Perkiraan total: " + Util.rupiah(total));
    }
}
