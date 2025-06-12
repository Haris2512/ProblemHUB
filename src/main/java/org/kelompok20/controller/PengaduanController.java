package org.kelompok20.controller;

import org.kelompok20.model.Pengaduan;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PengaduanController {
    // Dummy data pengaduan untuk simulasi (ganti dengan database di aplikasi nyata)
    private static List<Pengaduan> pengaduanList = new ArrayList<>();
    private static int nextId = 1; // Untuk ID pengaduan otomatis

    // Tambahkan beberapa data dummy awal untuk AdminDashboardView dan WargaDashboardView
    static {
        pengaduanList.add(new Pengaduan(nextId++, "Jalan Rusak", "Jl. Merdeka No. 10", "Aspal berlubang besar", "Belum Diproses", null, "warga"));
        pengaduanList.add(new Pengaduan(nextId++, "Lampu Mati", "Jl. Sudirman Gg. 5", "Lampu penerangan jalan mati", "Selesai", null, "warga"));
        pengaduanList.add(new Pengaduan(nextId++, "Saluran Tersumbat", "Komplek Melati Blok C", "Saluran air mampet, banjir", "Diproses", null, "warga2"));
        pengaduanList.add(new Pengaduan(nextId++, "Lainnya", "Terminal Bus", "Sampah menumpuk di area terminal", "Belum Diproses", null, "warga"));
    }

    public Pengaduan submitPengaduan(String kategori, String lokasi, String deskripsi, String fotoPath, String pelaporUsername) {
        Pengaduan newPengaduan = new Pengaduan(nextId++, kategori, lokasi, deskripsi, "Belum Diproses", fotoPath, pelaporUsername);
        pengaduanList.add(newPengaduan);
        return newPengaduan;
    }

    public List<Pengaduan> getAllPengaduan() {
        return new ArrayList<>(pengaduanList); // Mengembalikan salinan untuk mencegah modifikasi langsung
    }

    public List<Pengaduan> getPengaduanByUsername(String username) {
        return pengaduanList.stream()
                .filter(p -> p.getPelaporUsername().equals(username))
                .collect(Collectors.toList());
    }

    public boolean updatePengaduanStatus(int id, String newStatus) {
        Optional<Pengaduan> existingPengaduan = pengaduanList.stream()
                                            .filter(p -> p.getId() == id)
                                            .findFirst();
        if (existingPengaduan.isPresent()) {
            existingPengaduan.get().setStatus(newStatus);
            return true;
        }
        return false;
    }

    // Metode untuk mendapatkan pengaduan berdasarkan ID
    public Optional<Pengaduan> getPengaduanById(int id) {
        return pengaduanList.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }
}