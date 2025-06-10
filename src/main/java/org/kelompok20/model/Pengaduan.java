package org.kelompok20.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pengaduan {
    private int id;
    private String kategori;
    private String lokasi;
    private String deskripsi;
    private String status; // "Belum Diproses", "Diproses", "Selesai"
    private String fotoPath; // Path ke file foto yang diunggah
    private String pelaporUsername; // Username pelapor
}