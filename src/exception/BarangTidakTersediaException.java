package exception;

/**
 * Exception yang dilempar ketika barang tidak tersedia, tidak ditemukan,
 * atau tidak boleh diproses (misalnya barang sedang disewa penyewa lain).
 */
public class BarangTidakTersediaException extends Exception {

    /**
     * Membuat exception barang tidak tersedia.
     *
     * @param pesan pesan kesalahan yang ditampilkan ke pengguna
     */
    public BarangTidakTersediaException(String pesan) {
        super(pesan);
    }
}
