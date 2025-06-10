package org.kelompok20.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FormPengaduanView extends Application {
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

        Label fotoLabel = new Label("Belum ada foto");
        grid.add(fotoLabel, 1, 4);

        Button uploadButton = new Button("Unggah Foto");
        grid.add(uploadButton, 1, 5);
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                fotoLabel.setText(file.getName());
                // Nanti panggil FileHandler
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
                showAlert("Info", "Pengaduan dikirim: " + kategori + ", " + lokasi);
                primaryStage.close();
            }
        });

        cancelButton.setOnAction(e -> primaryStage.close());

        Scene scene = new Scene(grid, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Form Pengaduan");
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}