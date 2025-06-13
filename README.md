# 📢 LaporHub – Sistem Pengaduan Masyarakat Digital

*LaporHub* adalah sebuah aplikasi berbasis JavaFX yang dirancang untuk menjadi jembatan komunikasi antara masyarakat dan pihak pengelola layanan publik. Dengan LaporHub, warga dapat dengan mudah mengirimkan laporan mengenai masalah infrastruktur, layanan sosial, atau keluhan publik lainnya secara langsung kepada admin.


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


# Struktur Kelas - Aplikasi LaporHub

Berikut adalah struktur class utama dalam proyek *LaporHub*, dikelompokkan berdasarkan package yang digunakan:


## 📁 model/

- User.java 
- Admin.java (extends User)  
- Pengaduan.java 

## 📁 controller/ 

- AuthController.java  
- PengaduanController.java  

## 📁 view/ 

- AdminDashboardView.java
- WargaDashboardView.java
- LoginView.java
- FormPengaduanView.java
  
## 📁 utils/ 
- FileHandler.java  
## 📁 uploads/
- Berisi foto pengaduan yang dikirim warga

## Cara Menjalankan
1. Clone Repository
    https://github.com/haris2512/problemHUB
2. Compile semua file .java
3. Jalankan Main.java
4. Ikuti instruksi pada terminal

## Pengujian Aplikasi
Aplikasi **ProblemHUB** telah diuji untuk memastikan bahwa setiap fitur berfungsi dengan baik. Berikut adalah daftar pengujian yang telah dilakukan dan hasil yang diharapkan:

| No | **Deskripsi pengujian** | **Hasil yang diharapkan**                    | **Hasil** |
|----|-----------------------------|--------------------------------------------------------------------|--------|
| 1  | Registrasi & Login          | User baru berhasil daftar dan login ke dalam sistem               | Berhasil |
| 2  | Pengajuan Pengaduan         | Warga mengisi form lengkap dan unggah foto                        | Berhasil |
| 4  | Riwayat Pengaduan           | Warga melihat semua pengaduan yang pernah dikirim                 | Berhasil |
| 5  | Filter Laporan (Admin)      | Admin filter laporan berdasarkan status (Baru, Diproses, Selesai) | Berhasil |
| 6  | Ubah Status Laporan         | Admin ubah status laporan → tersimpan & tampil ke warga           | Berhasil |
| 7  | Logout                      | User logout dan kembali ke login                                  | Berhasil |
| 8  | Upload File Bukti           | File foto tersimpan dengan nama unik di folder `uploads/`         | Berhasil |

