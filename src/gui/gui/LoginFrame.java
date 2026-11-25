package gui;

import exception.LoginGagalException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.Admin;
import model.Akun;
import model.Penyewa;
import service.DataSistem;

/**
 * Jendela login. Setelah berhasil, membuka jendela Admin atau Penyewa.
 */
public class LoginFrame extends JFrame {
    private static final Color BACKGROUND = Theme.BACKGROUND;
    private static final Color TEXT = Theme.TEXT;

    /**
     * Membuat jendela login.
     *
     * @param data data sistem bersama
     */
    public LoginFrame(DataSistem data) {
        setTitle("Login - Rental Sepeda Listrik & Skuter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480, 370);
        setResizable(false);

        JPanel utama = new JPanel(new BorderLayout(0, 18));
        utama.setBackground(BACKGROUND);
        utama.setBorder(BorderFactory.createEmptyBorder(22, 28, 24, 28));

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 5));
        header.setBackground(BACKGROUND);
        JLabel judul = new JLabel("RENTAL SEPEDA LISTRIK", JLabel.CENTER);
        judul.setFont(new Font("Segoe UI", Font.BOLD, 21));
        judul.setForeground(Theme.GREEN);
        JLabel subjudul = new JLabel("Masuk untuk melanjutkan", JLabel.CENTER);
        subjudul.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subjudul.setForeground(Theme.MUTED);
        header.add(judul);
        header.add(subjudul);

        JTextField tfUser = new JTextField(18);
        JPasswordField pfPass = new JPasswordField(18);
        aturInput(tfUser);
        aturInput(pfPass);

        JPanel form = new JPanel(new GridLayout(0, 2, 12, 14));
        form.setBackground(Theme.SURFACE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                BorderFactory.createEmptyBorder(20, 18, 20, 18)));
        JLabel lblUser = label("Username");
        JLabel lblPass = label("Password");
        form.add(lblUser);
        form.add(tfUser);
        form.add(lblPass);
        form.add(pfPass);

        JLabel demo = new JLabel(
                "<html><div style='text-align:center'>Akun demo: admin / admin123 &nbsp; | &nbsp; budi / budi123 &nbsp; | &nbsp; sari / sari123</div></html>",
                JLabel.CENTER);
        demo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        demo.setForeground(Theme.MUTED);

        JButton btnLogin = new JButton("MASUK");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        Theme.gayaTombol(btnLogin);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(11, 18, 11, 18));

        JPanel bawah = new JPanel(new BorderLayout(0, 12));
        bawah.setBackground(BACKGROUND);
        bawah.add(demo, BorderLayout.CENTER);
        bawah.add(btnLogin, BorderLayout.SOUTH);

        JPanel tengah = new JPanel(new BorderLayout(0, 12));
        tengah.setBackground(BACKGROUND);
        tengah.add(form, BorderLayout.CENTER);
        tengah.add(bawah, BorderLayout.SOUTH);

        utama.add(header, BorderLayout.NORTH);
        utama.add(tengah, BorderLayout.CENTER);
        setContentPane(utama);

        ActionListener aksi = e -> {
            try {
                Akun akun = data.login(tfUser.getText().trim(), new String(pfPass.getPassword()));
                dispose();
                if (akun instanceof Admin) {
                    new AdminFrame(data, (Admin) akun).setVisible(true);
                } else {
                    new PenyewaFrame(data, (Penyewa) akun).setVisible(true);
                }
            } catch (LoginGagalException ex) {
                Util.error(this, ex.getMessage());
            }
        };
        btnLogin.addActionListener(aksi);
        pfPass.addActionListener(aksi);

        setLocationRelativeTo(null);
    }

    private JLabel label(String teks) {
        JLabel hasil = new JLabel(teks);
        hasil.setFont(new Font("Segoe UI", Font.BOLD, 13));
        hasil.setForeground(TEXT);
        return hasil;
    }

    private void aturInput(JTextField input) {
        input.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        input.setForeground(TEXT);
        input.setBackground(Theme.SURFACE_ALT);
        input.setCaretColor(Theme.GREEN);
        input.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                BorderFactory.createEmptyBorder(7, 9, 7, 9)));
    }
}
