package exception;

/**
 * Exception yang dilempar ketika proses login gagal
 * (username tidak ditemukan atau password salah).
 */
public class LoginGagalException extends Exception {

    /**
     * Membuat exception login gagal.
     *
     * @param pesan pesan kesalahan yang ditampilkan ke pengguna
     */
    public LoginGagalException(String pesan) {
        super(pesan);
    }
}
