package gui;

import exception.BarangTidakTersediaException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Admin;
import model.Barang;
import model.Invoice;
import model.SepedaListrik;
import model.SkuterListrik;
import model.SkuterPremium;
import model.Transaksi;
import service.DaftarBarang;
import service.DataSistem;

/**
 * Jendela utama admin: kelola barang, terima transaksi,
 * dan konfirmasi pengembalian beserta denda.
 */
public class AdminFrame extends JFrame {
    private final DataSistem data;
    private final Admin admin;

    private final DefaultTableModel modelBarang =
            Util.modelTabel("ID", "Nama", "Kategori", "Tarif/hari", "Status");
    private final DefaultTableModel modelMenunggu =
            Util.modelTabel("ID", "Penyewa", "Barang", "Lama (hari)", "Total");
    private final DefaultTableModel modelAktif =
            Util.modelTabel("ID", "Penyewa", "Barang", "Jatuh tempo");
    private final JTable tabelBarang = new JTable(modelBarang);
    private final JTable tabelMenunggu = new JTable(modelMenunggu);
    private final JTable tabelAktif = new JTable(modelAktif);
    private final JTextField tfTanggal = new JTextField(LocalDate.now().toString(), 10);
    private ArrayList<Transaksi> menunggu = new ArrayList<>();
    private ArrayList<Transaksi> aktif = new ArrayList<>();

    /**
     * Membuat jendela admin.
     *
     * @param data  data sistem bersama
     * @param admin admin yang sedang login
     */
    public AdminFrame(DataSistem data, Admin admin) {
        this.data = data;
        this.admin = admin;
        setTitle("Admin - " + admin.getUsername());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tab = new JTabbedPane();
        tab.addTab("Kelola barang", panelBarang());
        tab.addTab("Terima transaksi", panelMenunggu());
        tab.addTab("Pengembalian", panelAktif());
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
        setSize(800, 500);
        setLocationRelativeTo(null);
        refreshSemua();
    }

    private JPanel panelBarang() {
        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");
        btnTambah.addActionListener(e -> tambahBarang());
        btnEdit.addActionListener(e -> editBarang());
        btnHapus.addActionListener(e -> hapusBarang());
        JPanel aksi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        aksi.add(btnTambah);
        aksi.add(btnEdit);
        aksi.add(btnHapus);
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JScrollPane(tabelBarang), BorderLayout.CENTER);
        p.add(aksi, BorderLayout.SOUTH);
        return p;
    }

    private JPanel panelMenunggu() {
        JButton btnTerima = new JButton("Terima transaksi terpilih");
        btnTerima.addActionListener(e -> terima());
        JPanel aksi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        aksi.add(btnTerima);
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JScrollPane(tabelMenunggu), BorderLayout.CENTER);
        p.add(aksi, BorderLayout.SOUTH);
        return p;
    }

    private JPanel panelAktif() {
        JButton btnKembali = new JButton("Konfirmasi pengembalian");
        btnKembali.addActionListener(e -> kembali());
        JPanel aksi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        aksi.add(new JLabel("Tanggal kembali (yyyy-MM-dd):"));
        aksi.add(tfTanggal);
        aksi.add(btnKembali);
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JScrollPane(tabelAktif), BorderLayout.CENTER);
        p.add(aksi, BorderLayout.SOUTH);
        return p;
    }

    private void tambahBarang() {
        JComboBox<String> cbKategori =
                new JComboBox<>(new String[] {"Sepeda Listrik", "Skuter Listrik", "Skuter Premium"});
        JTextField tfId = new JTextField();
        JTextField tfNama = new JTextField();
        JTextField tfTarif = new JTextField();
        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.add(new JLabel("Kategori"));
        form.add(cbKategori);
        form.add(new JLabel("ID"));
        form.add(tfId);
        form.add(new JLabel("Nama"));
        form.add(tfNama);
        form.add(new JLabel("Tarif per hari"));
        form.add(tfTarif);
        int pilih = JOptionPane.showConfirmDialog(this, form, "Tambah barang",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (pilih != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            String id = tfId.getText().trim();
            String nama = tfNama.getText().trim();
            double tarif = Double.parseDouble(tfTarif.getText().trim());
            if (id.isEmpty() || nama.isEmpty() || tarif <= 0) {
                Util.error(this, "ID dan nama tidak boleh kosong, tarif harus lebih dari 0.");
                return;
            }
            DaftarBarang daftar = data.getDaftarBarang();
            if (daftar.adaId(id)) {
                Util.error(this, "ID " + id + " sudah dipakai.");
                return;
            }
            int k = cbKategori.getSelectedIndex();
            if (k == 0) {
                daftar.tambahBarang(new SepedaListrik(id, nama, tarif));
            } else if (k == 1) {
                daftar.tambahBarang(new SkuterListrik(id, nama, tarif));
            } else {
                daftar.tambahBarang(new SkuterPremium(id, nama, tarif));
            }
            refreshSemua();
        } catch (NumberFormatException ex) {
            Util.error(this, "Tarif harus berupa angka.");
        }
    }

    private void editBarang() {
        int baris = tabelBarang.getSelectedRow();
        if (baris < 0) {
            Util.error(this, "Pilih satu barang dulu.");
            return;
        }
        Barang b = data.getDaftarBarang().getSemuaBarang().get(baris);
        JTextField tfNama = new JTextField(b.getNama());
        JTextField tfTarif = new JTextField(String.valueOf((long) b.getTarifHarian()));
        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.add(new JLabel("Nama"));
        form.add(tfNama);
        form.add(new JLabel("Tarif per hari"));
        form.add(tfTarif);
        int pilih = JOptionPane.showConfirmDialog(this, form, "Edit " + b.getId(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (pilih != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            String nama = tfNama.getText().trim();
            double tarif = Double.parseDouble(tfTarif.getText().trim());
            if (nama.isEmpty() || tarif <= 0) {
                Util.error(this, "Nama tidak boleh kosong dan tarif harus lebih dari 0.");
                return;
            }
            data.getDaftarBarang().editBarang(b.getId(), nama, tarif);
            refreshSemua();
        } catch (NumberFormatException ex) {
            Util.error(this, "Tarif harus berupa angka.");
        } catch (BarangTidakTersediaException ex) {
            Util.error(this, ex.getMessage());
        }
    }

    private void hapusBarang() {
        int baris = tabelBarang.getSelectedRow();
        if (baris < 0) {
            Util.error(this, "Pilih satu barang dulu.");
            return;
        }
        Barang b = data.getDaftarBarang().getSemuaBarang().get(baris);
        int yakin = JOptionPane.showConfirmDialog(this, "Hapus " + b.getNama() + "?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION);
        if (yakin != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            data.getDaftarBarang().hapusBarang(b.getId());
            refreshSemua();
        } catch (BarangTidakTersediaException ex) {
            Util.error(this, ex.getMessage());
        }
    }

    private void terima() {
        int baris = tabelMenunggu.getSelectedRow();
        if (baris < 0) {
            Util.error(this, "Pilih satu transaksi dulu.");
            return;
        }
        Transaksi t = menunggu.get(baris);
        try {
            admin.terimaTransaksi(t);
            Util.tampilTeks(this, "Transaksi diterima", new Invoice(t).buatTeks());
        } catch (BarangTidakTersediaException ex) {
            Util.error(this, "Transaksi ditolak: " + ex.getMessage());
        }
        refreshSemua();
    }

    private void kembali() {
        int baris = tabelAktif.getSelectedRow();
        if (baris < 0) {
            Util.error(this, "Pilih satu transaksi dulu.");
            return;
        }
        Transaksi t = aktif.get(baris);
        try {
            LocalDate tgl = LocalDate.parse(tfTanggal.getText().trim());
            if (tgl.isBefore(t.getTanggalSewa())) {
                Util.error(this, "Tanggal kembali tidak boleh sebelum tanggal sewa.");
                return;
            }
            admin.konfirmasiKembali(t, tgl);
            Util.tampilTeks(this, "Pengembalian dikonfirmasi", new Invoice(t).buatTeks());
            refreshSemua();
        } catch (DateTimeParseException ex) {
            Util.error(this, "Format tanggal salah. Gunakan yyyy-MM-dd, contoh 2026-11-25.");
        }
    }

    private String ringkas(Transaksi t) {
        StringBuilder sb = new StringBuilder();
        for (Barang b : t.getBarang()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(b.getNama());
        }
        return sb.toString();
    }

    private void refreshSemua() {
        modelBarang.setRowCount(0);
        for (Barang b : data.getDaftarBarang().getSemuaBarang()) {
            modelBarang.addRow(new Object[] {b.getId(), b.getNama(), b.getKategori(),
                Util.rupiah(b.getTarifHarian()), b.isTersedia() ? "Tersedia" : "Disewa"});
        }
        modelMenunggu.setRowCount(0);
        menunggu = data.getTransaksiByStatus(Transaksi.MENUNGGU);
        for (Transaksi t : menunggu) {
            modelMenunggu.addRow(new Object[] {t.getId(), t.getPenyewa().getUsername(), ringkas(t),
                t.getLamaSewa(), Util.rupiah(t.hitungTotal())});
        }
        modelAktif.setRowCount(0);
        aktif = data.getTransaksiByStatus(Transaksi.AKTIF);
        for (Transaksi t : aktif) {
            modelAktif.addRow(new Object[] {t.getId(), t.getPenyewa().getUsername(), ringkas(t),
                t.getTanggalJatuhTempo()});
        }
    }
}
