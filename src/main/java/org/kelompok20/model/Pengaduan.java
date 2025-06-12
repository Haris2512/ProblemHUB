package org.kelompok20.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Kelas Pengaduan merepresentasikan entitas laporan pengaduan masyarakat.
// Ini adalah penerapan Encapsulation untuk entitas Pengaduan, sama seperti kelas User:
// - Field-field dibuat private.
// - Akses dan modifikasi data diatur melalui getter dan setter (dibuat oleh Lombok).
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pengaduan {
    private int id; // ID unik untuk setiap pengaduan
    private String kategori; // Kategori pengaduan (misal: "Jalan Rusak", "Lampu Mati")
    private String lokasi; // Lokasi kejadian pengaduan
    private String deskripsi; // Deskripsi detail pengaduan
    private String status; // Status pengaduan: "Belum Diproses", "Diproses", "Selesai"
    private String fotoPath; // Path relatif ke file foto yang diunggah (opsional)
    private String pelaporUsername; // Username pelapor yang mengajukan pengaduan
}
