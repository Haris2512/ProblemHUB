package org.kelompok20.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.kelompok20.controller.PengaduanController;
import org.kelompok20.utils.FileHandler;

import java.io.File;

public class FormPengaduanView extends Application {

    private PengaduanController pengaduanController = new PengaduanController();
    private FileHandler fileHandler = new FileHandler();
    private String currentFotoPath = null;
    private String pelaporUsername; // Untuk menyimpan username pelapor

    // Konstruktor untuk menerima username pelapor
    public FormPengaduanView(String pelaporUsername) {
        this.pelaporUsername = pelaporUsername;
    }

    // Default constructor diperlukan untuk launch()
    public FormPengaduanView() {
        // Jika dipanggil tanpa username, mungkin ini adalah mode testing atau pendaftaran anonim
    }


    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        Label titleLabel = new Label("Form Pengaduan Masyarakat");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        grid.add(titleLabel, 0, 0, 2, 1);

        Label kategoriLabel = new Label("Kategori:");
        grid.add(kategoriLabel, 0, 1);
        ComboBox<String> kategoriCombo = new ComboBox<>();
        kategoriCombo.getItems().addAll("Jalan Rusak", "Lampu Mati", "Saluran Tersumbat", "Lainnya");
        kategoriCombo.setValue("Jalan Rusak");
        grid.add(kategoriCombo, 1, 1);

        Label lokasiLabel = new Label("Lokasi:");
        grid.add(lokasiLabel, 0, 2);
        TextField lokasiField = new TextField();
        grid.add(lokasiField, 1, 2);

        Label deskripsiLabel = new Label("Deskripsi:");
        grid.add(deskripsiLabel, 0, 3);
        TextArea deskripsiArea = new TextArea();
        deskripsiArea.setPrefHeight(60);
        grid.add(deskripsiArea, 1, 3);

        Label fotoDescLabel = new Label("Foto (opsional):"); // Label untuk foto
        grid.add(fotoDescLabel, 0, 4);
        Label fotoStatusLabel = new Label("Belum ada foto dipilih."); // Label untuk menampilkan status foto
        grid.add(fotoStatusLabel, 1, 4);

        Button uploadButton = new Button("Unggah Foto");
        grid.add(uploadButton, 1, 5); // Tombol unggah di baris terpisah
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Pilih Foto Pengaduan");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                String savedPath = fileHandler.saveFile(selectedFile);
                if (savedPath != null) {
                    currentFotoPath = savedPath;
                    fotoStatusLabel.setText("Foto: " + selectedFile.getName());
                } else {
                    fotoStatusLabel.setText("Gagal mengunggah foto.");
                    currentFotoPath = null;
                }
            }
        });

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        grid.add(submitButton, 0, 6);
        grid.add(cancelButton, 1, 6);

        submitButton.setOnAction(e -> {
            String kategori = kategoriCombo.getValue();
            String lokasi = lokasiField.getText();
            String deskripsi = deskripsiArea.getText();

            if (lokasi.isEmpty() || deskripsi.isEmpty()) {
                showAlert("Error", "Lokasi dan Deskripsi harus diisi!");
            } else {
                // Asumsi pelaporUsername sudah di-set dari konstruktor LoginView
                String reporter = (pelaporUsername != null && !pelaporUsername.isEmpty()) ? pelaporUsername : "Anonim";
                pengaduanController.submitPengaduan(kategori, lokasi, deskripsi, currentFotoPath, reporter);
                showAlert("Info", "Pengaduan berhasil dikirim oleh " + reporter + "!");
                primaryStage.close();
                // Opsional: Kembali ke WargaDashboardView setelah submit
                // Jika ingin kembali ke dashboard warga setelah submit, Anda perlu
                // mempassing Stage atau memiliki mekanisme lain untuk memuat ulang data.
                // new WargaDashboardView(new User(pelaporUsername, "", "Warga")).start(new Stage());
            }
        });

        cancelButton.setOnAction(e -> primaryStage.close());

        Scene scene = new Scene(grid, 450, 400); // Ukuran lebih disesuaikan
        primaryStage.setScene(scene);
        primaryStage.setTitle("Form Pengaduan");

        primaryStage.setResizable(false); // Nonaktifkan resize untuk tampilan yang konsisten

        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}