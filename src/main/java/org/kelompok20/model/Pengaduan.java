package org.kelompok20.model;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class Pengaduan {
    private IntegerProperty id;
    private StringProperty kategori;
    private StringProperty lokasi;
    private StringProperty deskripsi;
    private StringProperty status;
    private StringProperty fotoPath;
    private StringProperty pelaporUsername;

    public Pengaduan(int id, String kategori, String lokasi, String deskripsi, String status, String fotoPath, String pelaporUsername) {
        this.id = new SimpleIntegerProperty(id);
        this.kategori = new SimpleStringProperty(kategori);
        this.lokasi = new SimpleStringProperty(lokasi);
        this.deskripsi = new SimpleStringProperty(deskripsi);
        this.status = new SimpleStringProperty(status);
        this.fotoPath = new SimpleStringProperty(fotoPath);
        this.pelaporUsername = new SimpleStringProperty(pelaporUsername);
    }

    // Getter dan Setter untuk id
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Getter dan Setter untuk kategori
    public String getKategori() {
        return kategori.get();
    }

    public void setKategori(String kategori) {
        this.kategori.set(kategori);
    }

    public StringProperty kategoriProperty() {
        return kategori;
    }

    // Getter dan Setter untuk lokasi
    public String getLokasi() {
        return lokasi.get();
    }

    public void setLokasi(String lokasi) {
        this.lokasi.set(lokasi);
    }

    public StringProperty lokasiProperty() {
        return lokasi;
    }

    // Getter dan Setter untuk deskripsi
    public String getDeskripsi() {
        return deskripsi.get();
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi.set(deskripsi);
    }

    public StringProperty deskripsiProperty() {
        return deskripsi;
    }

    // Getter dan Setter untuk status
    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }

    // Getter dan Setter untuk fotoPath
    public String getFotoPath() {
        return fotoPath.get();
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath.set(fotoPath);
    }

    public StringProperty fotoPathProperty() {
        return fotoPath;
    }

    // Getter dan Setter untuk pelaporUsername
    public String getPelaporUsername() {
        return pelaporUsername.get();
    }

    public void setPelaporUsername(String pelaporUsername) {
        this.pelaporUsername.set(pelaporUsername);
    }

    public StringProperty pelaporUsernameProperty() {
        return pelaporUsername;
    }

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

    private String status; 
    private String fotoPath; 
    private String pelaporUsername; 
}

    private String status; // "Belum Diproses", "Diproses", "Selesai"
    private String fotoPath; // Path ke file foto yang diunggah
    private String pelaporUsername; // Username pelapor

}

