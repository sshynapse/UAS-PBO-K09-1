import gui.LoginFrame;
import gui.Theme;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import service.DataSistem;

/**
 * Program utama versi GUI (Swing). Memakai logika yang sama dengan versi CLI.
 */
public class MainGUI {

    /**
     * Titik masuk program GUI.
     *
     * @param args tidak dipakai
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            // pakai tampilan bawaan Java jika tampilan tidak tersedia
        }
        Theme.setupDefaults();
        SwingUtilities.invokeLater(() -> new LoginFrame(new DataSistem()).setVisible(true));
    }
}
