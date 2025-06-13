package org.kelompok20.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.kelompok20.controller.PengaduanController;
import org.kelompok20.utils.FileHandler;
import org.kelompok20.service.IPengaduanService;

import java.io.File;

public class FormPengaduanView extends Application {

    private IPengaduanService pengaduanService = new PengaduanController();
    private FileHandler fileHandler = new FileHandler();
    private String currentFotoPath = null;
    private String pelaporUsername;
    private ImageView photoPreview;
    private Label photoStatusLabel;

    public FormPengaduanView(String pelaporUsername) {
        this.pelaporUsername = pelaporUsername;
    }

    public FormPengaduanView() {
        this.pelaporUsername = "Anonim";
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.getStyleClass().add("root");

        Label titleLabel = new Label("Form Pengaduan Masyarakat");
        titleLabel.getStyleClass().add("title-label");
        grid.add(titleLabel, 0, 0, 2, 1);

        Label kategoriLabel = new Label("Kategori:");
        grid.add(kategoriLabel, 0, 1);
        ComboBox<String> kategoriCombo = new ComboBox<>();
        kategoriCombo.getItems().addAll("Jalan Rusak", "Lampu Mati", "Saluran Tersumbat", "Lainnya");
        kategoriCombo.setValue("Jalan Rusak");
        kategoriCombo.getStyleClass().add("combo-box");
        grid.add(kategoriCombo, 1, 1);

        Label lokasiLabel = new Label("Lokasi:");
        grid.add(lokasiLabel, 0, 2);
        TextField lokasiField = new TextField();
        lokasiField.getStyleClass().add("text-field");
        grid.add(lokasiField, 1, 2);

        Label deskripsiLabel = new Label("Deskripsi:");
        grid.add(deskripsiLabel, 0, 3);
        TextArea deskripsiArea = new TextArea();
        deskripsiArea.setPrefHeight(60);
        deskripsiArea.getStyleClass().add("text-area");
        grid.add(deskripsiArea, 1, 3);

        Label fotoDescLabel = new Label("Foto (opsional):");
        grid.add(fotoDescLabel, 0, 4);

        photoPreview = new ImageView();
        photoPreview.setFitWidth(100);
        photoPreview.setPreserveRatio(true);
        grid.add(photoPreview, 1, 4);

        photoStatusLabel = new Label("Belum ada foto dipilih.");
        grid.add(photoStatusLabel, 1, 5);

        Button uploadButton = new Button("Unggah Foto");
        uploadButton.getStyleClass().add("button");
        grid.add(uploadButton, 1, 6);
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
                    photoStatusLabel.setText("Foto: " + selectedFile.getName());
                    try {
                        Image image = new Image(selectedFile.toURI().toString());
                        photoPreview.setImage(image);
                    } catch (Exception ex) {
                        System.err.println("Gagal memuat preview gambar: " + ex.getMessage());
                        photoPreview.setImage(null);
                    }
                } else {
                    photoStatusLabel.setText("Gagal mengunggah foto.");
                    currentFotoPath = null;
                    photoPreview.setImage(null);
                }
            }
        });

        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("button");
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("button-cancel");
        grid.add(submitButton, 0, 7);
        grid.add(cancelButton, 1, 7);

        submitButton.setOnAction(e -> {
            String kategori = kategoriCombo.getValue();
            String lokasi = lokasiField.getText();
            String deskripsi = deskripsiArea.getText();

            if (lokasi.isEmpty() || deskripsi.isEmpty()) {
                showAlert("Error", "Lokasi dan Deskripsi harus diisi!");
            } else {
                String reporter = (pelaporUsername != null && !pelaporUsername.isEmpty()) ? pelaporUsername : "Anonim";
                pengaduanService.submitPengaduan(kategori, lokasi, deskripsi, currentFotoPath, reporter);
                showAlert("Info", "Pengaduan berhasil dikirim oleh " + reporter + "!");
                primaryStage.close();
            }
        });

        cancelButton.setOnAction(e -> primaryStage.close());

        Scene scene = new Scene(grid, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
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
