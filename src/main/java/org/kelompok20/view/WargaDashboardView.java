package org.kelompok20.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import org.kelompok20.controller.PengaduanController;
import org.kelompok20.controller.AuthController; // Untuk logout
import org.kelompok20.model.Pengaduan;
import org.kelompok20.model.User;

public class WargaDashboardView extends Application {

    private PengaduanController pengaduanController = new PengaduanController();
    private AuthController authController = new AuthController(); // Instance AuthController
    private User currentUser; // Untuk menyimpan user yang sedang login

    // Konstruktor untuk menerima user yang login
    public WargaDashboardView(User user) {
        this.currentUser = user;
    }

    // Default constructor diperlukan karena Application class memanggil konstruktor tanpa argumen
    public WargaDashboardView() {
        // Jika dipanggil tanpa argumen, asumsi tidak ada user yang login atau perlu di-handle secara berbeda
        // Ini mungkin terjadi jika main() dipanggil langsung
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        Label titleLabel = new Label("Dashboard Warga");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        borderPane.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        TableView<Pengaduan> table = new TableView<>();
        // Tampilkan hanya pengaduan yang dibuat oleh user yang login
        if (currentUser != null) {
            table.setItems(FXCollections.observableArrayList(pengaduanController.getPengaduanByUsername(currentUser.getUsername())));
        } else {
            table.setItems(FXCollections.emptyObservableList()); // Jika tidak ada user, tabel kosong
            showAlert("Peringatan", "Tidak ada pengguna yang login. Menampilkan pengaduan kosong.");
        }


        TableColumn<Pengaduan, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        TableColumn<Pengaduan, String> kategoriCol = new TableColumn<>("Kategori");
        kategoriCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKategori()));

        TableColumn<Pengaduan, String> lokasiCol = new TableColumn<>("Lokasi");
        lokasiCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLokasi()));

        TableColumn<Pengaduan, String> deskripsiCol = new TableColumn<>("Deskripsi");
        deskripsiCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDeskripsi()));

        TableColumn<Pengaduan, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));


        table.getColumns().addAll(idCol, kategoriCol, lokasiCol, deskripsiCol, statusCol); // Tidak ada kolom Aksi untuk warga

        borderPane.setCenter(table);

        HBox buttonBox = new HBox(10);
        Button newPengaduanButton = new Button("Buat Pengaduan Baru");
        Button logoutButton = new Button("Logout");
        buttonBox.getChildren().addAll(newPengaduanButton, logoutButton);
        BorderPane.setAlignment(buttonBox, Pos.CENTER); // Pusatkan buttonBox
        borderPane.setBottom(buttonBox);

        newPengaduanButton.setOnAction(e -> {
            // Saat membuat pengaduan baru, kirim username pelapor
            String usernameToPass = (currentUser != null) ? currentUser.getUsername() : "Anonim";
            Stage formStage = new Stage();
            FormPengaduanView formView = new FormPengaduanView(usernameToPass);
            formView.start(formStage);

            // Menambahkan listener agar dashboard di-refresh saat form pengaduan ditutup
            formStage.setOnHiding(event -> {
                // Refresh data tabel setelah form ditutup
                if (currentUser != null) {
                    table.setItems(FXCollections.observableArrayList(pengaduanController.getPengaduanByUsername(currentUser.getUsername())));
                }
            });
        });

        logoutButton.setOnAction(e -> {
            authController.logout(); // Logout user dari controller
            primaryStage.close();
            new LoginView().start(new Stage());
        });

        Scene scene = new Scene(borderPane, 600, 400); // Ukuran disesuaikan
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard Warga");
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