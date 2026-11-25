package model;

/**
 * Class abstrak yang mewakili akun pengguna sistem rental.
 * Diturunkan oleh {@link Admin} dan {@link Penyewa}.
 */
public abstract class Akun {
    private String id;
    private String username;
    private String password;

    /**
     * Membuat akun baru.
     *
     * @param id       ID unik akun
     * @param username username untuk login
     * @param password password untuk login
     */
    public Akun(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * @return ID akun
     */
    public String getId() {
        return id;
    }

    /**
     * @return username akun
     */
    public String getUsername() {
        return username;
    }

    /**
     * Memeriksa apakah username dan password cocok dengan akun ini.
     *
     * @param username username yang diinput
     * @param password password yang diinput
     * @return true jika keduanya cocok, false jika salah satunya salah
     */
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}
