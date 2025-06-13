package org.kelompok20.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonDataManager<T> {

    private final ObjectMapper objectMapper;
    private final String filename;
    private final Class<T> type;

    public JsonDataManager(String filename, Class<T> type) {
        this.filename = filename;
        this.type = type;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public List<T> loadData() {
        File file = new File(filename);
        if (!file.exists() || file.length() == 0) {
            System.out.println("File JSON tidak ditemukan atau kosong: " + filename + ". Menginisialisasi dengan list kosong.");
            return new ArrayList<>();
        }
        try {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return objectMapper.readValue(file, listType);
        } catch (IOException e) {
            System.err.println("Error saat memuat data dari " + filename + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveData(List<T> data) {
        try {
            objectMapper.writeValue(new File(filename), data);
        } catch (IOException e) {
            System.err.println("Error saat menyimpan data ke " + filename + ": " + e.getMessage());
        }
    }
}
