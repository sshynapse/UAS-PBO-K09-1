import exception.LoginGagalException;
import java.util.Scanner;
import model.Admin;
import model.Akun;
import model.Penyewa;
import service.DataSistem;

/**
 * Program utama versi CLI: menu login lalu mengarahkan ke menu Admin atau Penyewa.
 */
public class Main {
    private static Akun akunAktif;

    /**
     * Titik masuk program CLI.
     *
     * @param args tidak dipakai
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DataSistem data = new DataSistem();
        System.out.println("=== RENTAL SEPEDA LISTRIK & SKUTER ===");
        boolean jalan = true;
        while (jalan) {
            System.out.println("\n1. Login");
            System.out.println("2. Keluar");
            System.out.print("Pilih: ");
            String pilih = sc.nextLine().trim();
            if (pilih.equals("1")) {
                login(data, sc);
            } else if (pilih.equals("2")) {
                jalan = false;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
        System.out.println("Terima kasih.");
        sc.close();
    }

    /**
     * Meminta username dan password, lalu membuka menu sesuai jenis akun.
     *
     * @param data data sistem bersama
     * @param sc   scanner untuk input
     */
    public static void login(DataSistem data, Scanner sc) {
        System.out.print("Username: ");
        String u = sc.nextLine().trim();
        System.out.print("Password: ");
        String p = sc.nextLine().trim();
        try {
            akunAktif = data.login(u, p);
            if (akunAktif instanceof Admin) {
                ((Admin) akunAktif).tampilMenu(data, sc);
            } else {
                ((Penyewa) akunAktif).tampilMenu(data, sc);
            }
            akunAktif = null;
        } catch (LoginGagalException e) {
            System.out.println("Login gagal: " + e.getMessage());
        }
    }
}
