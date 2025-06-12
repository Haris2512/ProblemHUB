package org.kelompok20.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHandler {

    private static final String UPLOAD_DIR = "uploads"; // Folder untuk menyimpan file

    public FileHandler() {
        // Pastikan folder uploads ada
        File uploadFolder = new File(UPLOAD_DIR);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs(); // Buat folder jika belum ada
        }
    }

    /**
     * Menyimpan file yang diunggah ke direktori aplikasi.
     * @param sourceFile File sumber yang diungunggah
     * @return Path absolut dari file yang disimpan, atau null jika gagal
     */
    public String saveFile(File sourceFile) {
        if (sourceFile == null) {
            return null;
        }

        try {
            // Buat nama file unik untuk menghindari overwrite
            String fileName = System.currentTimeMillis() + "_" + sourceFile.getName();
            Path destinationPath = Paths.get(UPLOAD_DIR, fileName);

            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return destinationPath.toAbsolutePath().toString(); // Mengembalikan path absolut
        } catch (IOException e) {
            System.err.println("Gagal menyimpan file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Mendapatkan objek File dari path yang disimpan.
     * @param filePath Path file yang disimpan
     * @return Objek File, atau null jika file tidak ditemukan
     */
    public File getFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }
        File file = new File(filePath);
        return file.exists() ? file : null;
    }
}