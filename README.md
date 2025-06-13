# 📢 ProblemHUB – Sistem Pengaduan Masyarakat Digital

*ProblemHub* adalah sebuah aplikasi berbasis JavaFX yang dirancang untuk menjadi jembatan komunikasi antara masyarakat dan pihak pengelola layanan publik. Dengan ProblemHub, warga dapat dengan mudah mengirimkan laporan mengenai masalah infrastruktur, layanan sosial, atau keluhan publik lainnya secara langsung kepada admin.


## Fitur Aplikasi

### 1. Login & Registrasi
- Autentikasi pengguna melalui sistem login untuk dua peran utama: *Warga* dan *Admin*.
- Warga dapat mendaftar akun baru untuk mulai mengakses fitur laporan.
- Admin memiliki akses penuh terhadap data laporan dan sistem manajemen pengaduan.

### 2. Formulir Pengaduan
- Warga dapat mengisi formulir pengaduan dengan detail sebagai berikut:
  - Judul Laporan
  - Kategori Laporan (Jalan, Air, Listrik, Sosial, dll)
  - Lokasi kejadian
  - Deskripsi lengkap
  - Upload bukti foto (jika ada)
- Validasi input dilakukan untuk memastikan data yang dikirim lengkap dan sah.

### 3. Riwayat & Status Pengaduan
- Setiap pengaduan akan masuk ke sistem dan diberi status otomatis:
  - Terkirim
  - Diproses
  - Selesai
- Warga dapat melihat riwayat pengaduan mereka secara real-time.


### 4. Dashboard Admin
- Tampilan khusus untuk admin memuat seluruh data laporan warga.
- Fitur pencarian dan filter:
  - Berdasarkan status, tanggal, atau kategori laporan.


# Struktur Project - Aplikasi ProblemHub

```plaintext
app/
├── src/main/java/org/kelompok20/
│   ├── controller/
│   │   ├── AuthController.java
│   │   └── PengaduanController.java
│   │
│   ├── model/
│   │   ├── Admin.java
│   │   ├── User.java
│   │   └── Pengaduan.java
│   │
│   ├── service/
│   │   └── IPengaduanService.java
│   │
│   ├── utils/
│   │   ├── FileHandler.java
│   │   └── JsonDataManager.java
│   │
│   ├── view/
│   │   ├── AdminDashboard.java
│   │   ├── FormPengaduan.java
│   │   ├── LoginView.java
│   │   └── WargaDashboard.java
│   │
│   └── App.java            # Entry point aplikasi
│
├── resources/
└── data/

```
## Penerapan Pilar OOP


| Pilar OOP      | Implementasi dalam Aplikasi ProblemHub                                          |
|----------------|----------------------------------------------------------------------------------|
| **Encapsulation** | Menggunakan modifier `private` dan `getter/setter` di kelas model (`User`, `Pengaduan`) |
| **Inheritance**   | `User` dapat diturunkan menjadi `Admin` atau role khusus lain jika dikembangkan |
| **Abstraction**   | Interface `IPengaduanService` mendefinisikan kontrak layanan pengaduan         |
| **Polymorphism**  | Implementasi method `toString()` pada objek pengaduan dan polymorphic behavior lainnya |

## Cara Menjalankan Aplikasi

1. **Clone Repository**
   git clone https://github.com/haris2512/problemHUB
   Atau bisa juga dengan cara:
   Klik tombol Code > Download ZIP
   Ekstrak file ZIP ke folder pilihan Anda
2. **Compile Semua File .java**
   Kamu bisa menggunakan IDE seperti IntelliJ IDEA / VSCode atau langsung melalui terminal.
   Jika menggunakan terminal, pastikan JavaFX sudah terpasang, lalu jalankan:
   ```Bash
   ./gradlew build
   ./gradlew run
   ```
3. **Gunakan Aplikasi**
   - Login sebagai warga untuk membuat laporan.
   - Login sebagai admin untuk melihat dan memproses laporan dari masyarakat.

## Kontributor
- H071241061 Naa'ilah Mazaya
- H071241070 Haris
- H071241091 Novelin Fitri Phandika