package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.JTableHeader;

/**
 * Pengaturan warna dan gaya tampilan (dark mode) untuk seluruh halaman GUI.
 * Aksen utama memakai hijau neon, aksen kedua memakai biru.
 */
public final class Theme {
    /** Latar paling belakang. */
    public static final Color BACKGROUND = new Color(13, 17, 28);
    /** Latar kartu, tabel, dan panel. */
    public static final Color SURFACE = new Color(22, 29, 45);
    /** Latar baris selang-seling dan header tabel. */
    public static final Color SURFACE_ALT = new Color(30, 40, 62);
    /** Warna garis pembatas. */
    public static final Color BORDER = new Color(48, 61, 90);
    /** Warna teks utama. */
    public static final Color TEXT = new Color(230, 235, 245);
    /** Warna teks redup. */
    public static final Color MUTED = new Color(138, 150, 173);
    /** Aksen utama: hijau neon. */
    public static final Color GREEN = new Color(0, 230, 160);
    /** Aksen hijau saat disorot. */
    public static final Color GREEN_HOVER = new Color(90, 255, 200);
    /** Aksen kedua: biru neon. */
    public static final Color BLUE = new Color(56, 160, 255);
    /** Warna teks di atas tombol hijau. */
    public static final Color ON_ACCENT = new Color(4, 28, 22);
    /** Warna pilihan baris. */
    public static final Color SELECTION = new Color(24, 70, 110);

    private static final String FONT = "Segoe UI";

    private Theme() {
    }

    /**
     * Mengatur warna komponen Swing sebelum jendela dibuat,
     * termasuk dialog (JOptionPane) agar ikut gelap.
     */
    public static void setupDefaults() {
        Font biasa = new Font(FONT, Font.PLAIN, 13);
        Font tebal = new Font(FONT, Font.BOLD, 12);

        for (String kunci : new String[] {"Panel", "OptionPane", "RootPane", "Viewport", "ScrollPane",
            "OptionPane.messagePane"}) {
            UIManager.put(kunci + ".background", new ColorUIResource(BACKGROUND));
        }
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(TEXT));
        UIManager.put("Label.foreground", new ColorUIResource(TEXT));
        UIManager.put("Label.font", new FontUIResource(biasa));
        UIManager.put("Button.font", new FontUIResource(tebal));
        UIManager.put("Button.background", new ColorUIResource(SURFACE_ALT));
        UIManager.put("Button.foreground", new ColorUIResource(TEXT));
        UIManager.put("Button.select", new ColorUIResource(SELECTION));
        UIManager.put("Button.focus", new ColorUIResource(GREEN));
        UIManager.put("Button.light", new ColorUIResource(BORDER));
        UIManager.put("Button.highlight", new ColorUIResource(BORDER));
        UIManager.put("Button.shadow", new ColorUIResource(BORDER));
        UIManager.put("Button.darkShadow", new ColorUIResource(BORDER));

        for (String kunci : new String[] {"TextField", "PasswordField", "TextArea", "Spinner",
            "FormattedTextField", "ComboBox"}) {
            UIManager.put(kunci + ".background", new ColorUIResource(SURFACE_ALT));
            UIManager.put(kunci + ".foreground", new ColorUIResource(TEXT));
            UIManager.put(kunci + ".caretForeground", new ColorUIResource(GREEN));
            UIManager.put(kunci + ".selectionBackground", new ColorUIResource(SELECTION));
            UIManager.put(kunci + ".selectionForeground", new ColorUIResource(TEXT));
        }
        UIManager.put("ComboBox.buttonBackground", new ColorUIResource(SURFACE_ALT));
        UIManager.put("ComboBox.buttonDarkShadow", new ColorUIResource(BORDER));
        UIManager.put("ComboBox.buttonHighlight", new ColorUIResource(SURFACE_ALT));
        UIManager.put("ComboBox.buttonShadow", new ColorUIResource(SURFACE_ALT));
        UIManager.put("Spinner.arrowButtonBackground", new ColorUIResource(SURFACE_ALT));
        UIManager.put("Spinner.border", BorderFactory.createLineBorder(BORDER));
        UIManager.put("ComboBox.border", BorderFactory.createLineBorder(BORDER));

        UIManager.put("TabbedPane.background", new ColorUIResource(SURFACE));
        UIManager.put("TabbedPane.foreground", new ColorUIResource(MUTED));
        UIManager.put("TabbedPane.selected", new ColorUIResource(SURFACE_ALT));
        UIManager.put("TabbedPane.contentAreaColor", new ColorUIResource(BACKGROUND));
        UIManager.put("TabbedPane.light", new ColorUIResource(BORDER));
        UIManager.put("TabbedPane.highlight", new ColorUIResource(GREEN));
        UIManager.put("TabbedPane.shadow", new ColorUIResource(BORDER));
        UIManager.put("TabbedPane.darkShadow", new ColorUIResource(BORDER));
        UIManager.put("TabbedPane.focus", new ColorUIResource(GREEN));
        UIManager.put("TabbedPane.borderHightlightColor", new ColorUIResource(BORDER));

        UIManager.put("ScrollBar.background", new ColorUIResource(SURFACE));
        UIManager.put("ScrollBar.thumb", new ColorUIResource(BORDER));
        UIManager.put("ScrollBar.thumbShadow", new ColorUIResource(BORDER));
        UIManager.put("ScrollBar.thumbHighlight", new ColorUIResource(BORDER));
        UIManager.put("ScrollBar.thumbDarkShadow", new ColorUIResource(BORDER));
        UIManager.put("ScrollBar.track", new ColorUIResource(SURFACE));
        UIManager.put("ScrollBar.trackHighlight", new ColorUIResource(SURFACE));

        UIManager.put("Table.background", new ColorUIResource(SURFACE));
        UIManager.put("Table.foreground", new ColorUIResource(TEXT));
        UIManager.put("Table.gridColor", new ColorUIResource(BORDER));
        UIManager.put("Table.selectionBackground", new ColorUIResource(SELECTION));
        UIManager.put("Table.selectionForeground", new ColorUIResource(TEXT));
        UIManager.put("TableHeader.background", new ColorUIResource(SURFACE_ALT));
        UIManager.put("TableHeader.foreground", new ColorUIResource(GREEN));

        UIManager.put("ToolTip.background", new ColorUIResource(SURFACE_ALT));
        UIManager.put("ToolTip.foreground", new ColorUIResource(TEXT));
    }

    /**
     * Membuat tombol bergaya tema (hijau neon, teks gelap, ada efek sorot).
     *
     * @param tombol tombol yang akan digayakan
     */
    public static void gayaTombol(JButton tombol) {
        tombol.setUI(new BasicButtonUI());
        tombol.setBackground(GREEN);
        tombol.setForeground(ON_ACCENT);
        tombol.setFont(new Font(FONT, Font.BOLD, 12));
        tombol.setFocusPainted(false);
        tombol.setBorder(new EmptyBorder(8, 14, 8, 14));
        tombol.setOpaque(true);
        tombol.setContentAreaFilled(true);
        tombol.setBorderPainted(false);
        tombol.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        if (tombol.getClientProperty("tema.hover") == null) {
            tombol.putClientProperty("tema.hover", Boolean.TRUE);
            tombol.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    tombol.setBackground(GREEN_HOVER);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    tombol.setBackground(GREEN);
                }
            });
        }
    }

    /**
     * Menerapkan tema ke seluruh komponen di dalam jendela.
     *
     * @param frame jendela yang akan diberi tema
     */
    public static void apply(JFrame frame) {
        frame.getContentPane().setBackground(BACKGROUND);
        styleContainer(frame.getContentPane());
    }

    private static void styleContainer(Container parent) {
        for (Component component : parent.getComponents()) {
            if (component instanceof JPanel) {
                component.setBackground(BACKGROUND);
                component.setForeground(TEXT);
            }

            if (component instanceof JLabel) {
                component.setForeground(TEXT);
                component.setFont(new Font(FONT, Font.PLAIN, 13));
            }

            if (component instanceof JButton) {
                gayaTombol((JButton) component);
            }

            if (component instanceof JTextField || component instanceof JPasswordField
                    || component instanceof JComboBox) {
                component.setBackground(SURFACE_ALT);
                component.setForeground(TEXT);
                component.setFont(new Font(FONT, Font.PLAIN, 13));
            }

            if (component instanceof JTabbedPane) {
                JTabbedPane tabs = (JTabbedPane) component;
                tabs.setBackground(SURFACE);
                tabs.setForeground(MUTED);
                tabs.setFont(new Font(FONT, Font.BOLD, 13));
            }

            if (component instanceof JTable) {
                JTable table = (JTable) component;
                table.setBackground(SURFACE);
                table.setForeground(TEXT);
                table.setSelectionBackground(SELECTION);
                table.setSelectionForeground(TEXT);
                table.setGridColor(BORDER);
                table.setRowHeight(28);
                table.setFont(new Font(FONT, Font.PLAIN, 12));
                JTableHeader header = table.getTableHeader();
                header.setBackground(SURFACE_ALT);
                header.setForeground(GREEN);
                header.setFont(new Font(FONT, Font.BOLD, 12));
                header.setReorderingAllowed(false);
            }

            if (component instanceof JScrollPane) {
                JScrollPane gulir = (JScrollPane) component;
                gulir.setBackground(SURFACE);
                gulir.getViewport().setBackground(SURFACE);
                gulir.setBorder(BorderFactory.createLineBorder(BORDER));
            }

            if (component instanceof JComboBox || component instanceof JSpinner) {
                component.setBackground(SURFACE_ALT);
                component.setForeground(TEXT);
                continue;
            }

            if (component instanceof Container) {
                styleContainer((Container) component);
            }
        }
    }
}
