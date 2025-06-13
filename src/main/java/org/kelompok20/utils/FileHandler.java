package org.kelompok20.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHandler {

    private static final String UPLOAD_DIR = "data/uploads";

    public FileHandler() {
        File uploadFolder = new File(UPLOAD_DIR);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }
    }

    public String saveFile(File sourceFile) {
        if (sourceFile == null) {
            return null;
        }

        try {
            String fileName = System.currentTimeMillis() + "_" + sourceFile.getName();
            Path destinationPath = Paths.get(UPLOAD_DIR, fileName);

            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return Paths.get(UPLOAD_DIR, fileName).toString();
        } catch (IOException e) {
            System.err.println("Gagal menyimpan file: " + e.getMessage());
            return null;
        }
    }

    public File getFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }
        Path fullPath = Paths.get(filePath);
        if (!fullPath.isAbsolute() && !fullPath.startsWith(UPLOAD_DIR)) {
            fullPath = Paths.get(UPLOAD_DIR, filePath);
        }

        File file = fullPath.toFile();
        return file.exists() ? file : null;
    }
}
