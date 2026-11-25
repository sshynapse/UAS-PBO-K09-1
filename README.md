# Rental Sepeda Listrik & Skuter

## Deskripsi
Sistem penyewaan sepeda listrik dan skuter menggunakan bahasa Java.
Penyewa dapat melihat barang, memasukkannya ke keranjang, lalu melakukan transaksi sewa
dengan memilih lama sewa dan metode pembayaran. Admin mengelola barang, menerima transaksi,
dan mengonfirmasi pengembalian beserta denda keterlambatan.
Dibuat untuk UAS Praktikum Pemrograman Berorientasi Objek.

Program utama sesuai soal adalah versi **CLI** (`Main`). Versi **GUI** (`MainGUI`, Swing)
adalah tambahan opsional yang memakai logika yang sama.

## Fitur
- Login Admin dan Penyewa
- Kelola barang (tambah, edit, hapus), keranjang sewa, transaksi, invoice, riwayat sewa
- 3 kategori barang dengan cara hitung biaya berbeda:
  Sepeda Listrik (tarif x hari), Skuter Listrik (+ asuransi 5%), Skuter Premium (+ Rp10.000/hari helm dan baterai)
- 3 metode pembayaran: Transfer Bank, QRIS, Tunai
- Denda keterlambatan Rp20.000 per hari per barang
- Barang yang sedang disewa tidak bisa disewa penyewa lain

## Anggota Kelompok
| Nama | NRP |
|---|---|
| Shofy | ... |
| Kania | ... |
| Riski | ... |

## Cara Menjalankan
Compile (dari folder utama proyek):

    javac -d out src/*.java src/*/*.java

Jalankan versi CLI:

    java -cp out Main

Jalankan versi GUI (opsional):

    java -cp out MainGUI

Di PowerShell, compile dengan:

    javac -d out (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })

## Akun Demo
| Peran | Username | Password |
|---|---|---|
| Admin | admin | admin123 |
| Penyewa | budi | budi123 |
| Penyewa | sari | sari123 |

## Struktur
    src/
    ├── Main.java            (CLI)
    ├── MainGUI.java         (GUI, opsional)
    ├── model/               Akun, Admin, Penyewa, Barang, SepedaListrik, SkuterListrik, SkuterPremium, Transaksi, Invoice
    ├── payment/             Pembayaran (interface), TransferBank, QRIS, Tunai
    ├── service/             DataSistem, KeranjangSewa, DaftarBarang
    ├── exception/           LoginGagalException, BarangTidakTersediaException
    └── gui/                 LoginFrame, PenyewaFrame, AdminFrame, Util (opsional)
    docs/class-diagram.puml
