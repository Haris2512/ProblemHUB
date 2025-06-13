package org.kelompok20.controller;

import org.kelompok20.model.Pengaduan;
import org.kelompok20.service.IPengaduanService;
import org.kelompok20.utils.JsonDataManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PengaduanController implements IPengaduanService {
    private static final String PENGADUAN_FILE = "data/pengaduans.json";
    private JsonDataManager<Pengaduan> pengaduanDataManager;
    private int nextId;

    public PengaduanController() {
        pengaduanDataManager = new JsonDataManager<>(PENGADUAN_FILE, Pengaduan.class);
        List<Pengaduan> existingPengaduan = pengaduanDataManager.loadData();
        if (existingPengaduan.isEmpty()) {
            nextId = 1;
        } else {
            nextId = existingPengaduan.stream()
                        .mapToInt(Pengaduan::getId)
                        .max()
                        .orElse(0) + 1;
        }
    }

    @Override
    public Pengaduan submitPengaduan(String kategori, String lokasi, String deskripsi, String fotoPath, String pelaporUsername) {
        List<Pengaduan> pengaduanList = pengaduanDataManager.loadData();
        Pengaduan newPengaduan = new Pengaduan(nextId++, kategori, lokasi, deskripsi, "Belum Diproses", fotoPath, pelaporUsername);
        pengaduanList.add(newPengaduan);
        pengaduanDataManager.saveData(pengaduanList);
        return newPengaduan;
    }

    @Override
    public List<Pengaduan> getAllPengaduan() {
        return new ArrayList<>(pengaduanDataManager.loadData());
    }

    @Override
    public List<Pengaduan> getPengaduanByUsername(String username) {
        return pengaduanDataManager.loadData().stream()
                .filter(p -> p.getPelaporUsername().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public boolean updatePengaduanStatus(int id, String newStatus) {
        List<Pengaduan> pengaduanList = pengaduanDataManager.loadData();
        Optional<Pengaduan> existingPengaduan = pengaduanList.stream()
                                            .filter(p -> p.getId() == id)
                                            .findFirst();
        if (existingPengaduan.isPresent()) {
            existingPengaduan.get().setStatus(newStatus);
            pengaduanDataManager.saveData(pengaduanList);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Pengaduan> getPengaduanById(int id) {
        return pengaduanDataManager.loadData().stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }
}
