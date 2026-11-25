package model;

public package model;

/**
 * Class abstrak untuk menyimpan data akun pengguna.
 */
public abstract class Akun {
    private String id;
    private String username;
    private String password;

    /**
     * Constructor untuk membuat akun baru.
     */
    public Akun(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Memeriksa kecocokan username dan password.
     */
    public boolean login(String username, String password) {
        return this.username.equals(username)
                && this.password.equals(password);
    }
} {
    
}
