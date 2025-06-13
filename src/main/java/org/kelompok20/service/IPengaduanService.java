package org.kelompok20.service;

import org.kelompok20.model.Pengaduan;

import java.util.List;
import java.util.Optional;

public interface IPengaduanService {
    Pengaduan submitPengaduan(String kategori, String lokasi, String deskripsi, String fotoPath, String pelaporUsername);
    List<Pengaduan> getAllPengaduan();
    List<Pengaduan> getPengaduanByUsername(String username);
    boolean updatePengaduanStatus(int id, String newStatus);
    Optional<Pengaduan> getPengaduanById(int id);
}
