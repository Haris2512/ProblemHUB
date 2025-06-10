package org.kelompok20.view;

import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import org.kelompok20.controller.PengaduanController;
import org.kelompok20.controller.AuthController; // Untuk logout
import org.kelompok20.model.Pengaduan;
import org.kelompok20.model.User;

public class AdminDashboardView extends Application {

    private PengaduanController pengaduanController = new PengaduanController();
    private AuthController authController = new AuthController(); // Instance AuthController
    private ObservableList<Pengaduan> masterPengaduanList;
    private User currentUser; // Untuk menyimpan user yang sedang login
    private ComboBox<String> statusFilterCombo; // Deklarasi di scope kelas

    // Konstruktor untuk menerima user yang login
    public AdminDashboardView(User user) {
        this.currentUser = user;
    }

    // Default constructor diperlukan karena Application class memanggil konstruktor tanpa argumen
    public AdminDashboardView() {
        // Jika dipanggil tanpa argumen, asumsi tidak ada user yang login atau perlu di-handle secara berbeda
        // Ini mungkin terjadi jika main() dipanggil langsung
    }

    @Override
    public void start(Stage primaryStage) {
        // Inisialisasi data pengaduan
        masterPengaduanList = FXCollections.observableArrayList(pengaduanController.getAllPengaduan());

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        Label titleLabel = new Label("Dashboard Admin");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        HBox filterBox = new HBox(10);
        statusFilterCombo = new ComboBox<>(FXCollections.observableArrayList("Semua", "Belum Diproses", "Diproses", "Selesai"));
        statusFilterCombo.setValue("Semua"); // Default selected value
        filterBox.getChildren().addAll(new Label("Filter Status:"), statusFilterCombo);

        // Menggabungkan titleLabel dan filterBox dalam VBox untuk bagian TOP
        VBox topContainer = new VBox(10); // Spasi vertikal 10px
        topContainer.getChildren().addAll(titleLabel, filterBox);
        topContainer.setAlignment(Pos.CENTER); // Pusatkan konten di VBox

        borderPane.setTop(topContainer);

        TableView<Pengaduan> table = new TableView<>();
        table.setItems(masterPengaduanList); // Gunakan ObservableList Pengaduan

        // Definisi Kolom
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

        TableColumn<Pengaduan, String> pelaporCol = new TableColumn<>("Pelapor");
        pelaporCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPelaporUsername()));

        TableColumn<Pengaduan, Void> aksiCol = new TableColumn<>("Aksi");
        aksiCol.setCellFactory(param -> new TableCell<Pengaduan, Void>() {
            private final Button btn = new Button("Ubah Status");

            {
                btn.setOnAction(event -> {
                    Pengaduan pengaduan = getTableView().getItems().get(getIndex());
                    showStatusChangeDialog(table, pengaduan); // Pass table to dialog
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        table.getColumns().addAll(idCol, kategoriCol, lokasiCol, deskripsiCol, statusCol, pelaporCol, aksiCol);

        borderPane.setCenter(table);

        Button logoutButton = new Button("Logout");
        BorderPane.setAlignment(logoutButton, Pos.BOTTOM_RIGHT);
        borderPane.setBottom(logoutButton);

        // Event handler untuk filter status
        statusFilterCombo.setOnAction(e -> {
            filterTable(table, statusFilterCombo.getValue());
        });

        logoutButton.setOnAction(e -> {
            authController.logout(); // Logout user dari controller
            primaryStage.close();
            new LoginView().start(new Stage());
        });

        Scene scene = new Scene(borderPane, 800, 500); // Ukuran jendela lebih besar
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard Admin");
        primaryStage.show();
    }

    private void filterTable(TableView<Pengaduan> table, String status) {
        if (status.equals("Semua")) {
            table.setItems(masterPengaduanList);
        } else {
            ObservableList<Pengaduan> filteredList = FXCollections.observableArrayList();
            for (Pengaduan p : masterPengaduanList) {
                if (p.getStatus().equals(status)) {
                    filteredList.add(p);
                }
            }
            table.setItems(filteredList);
        }
    }

    private void showStatusChangeDialog(TableView<Pengaduan> table, Pengaduan pengaduan) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Ubah Status Pengaduan");
        dialog.setHeaderText("Pengaduan ID: " + pengaduan.getId() + "\n" +
                             "Kategori: " + pengaduan.getKategori() + "\n" +
                             "Lokasi: " + pengaduan.getLokasi());

        // Set the button types.
        ButtonType applyButtonType = new ButtonType("Terapkan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

        // Create the status selection combo box.
        ComboBox<String> statusComboBox = new ComboBox<>(FXCollections.observableArrayList("Belum Diproses", "Diproses", "Selesai"));
        statusComboBox.setValue(pengaduan.getStatus());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Status Baru:"), 0, 0);
        grid.add(statusComboBox, 1, 0);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a status string when the apply button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyButtonType) {
                return statusComboBox.getValue();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(newStatus -> {
            if (pengaduanController.updatePengaduanStatus(pengaduan.getId(), newStatus)) {
                // Perbarui ObservableList agar TableView juga terupdate
                // Cari pengaduan di master list dan update statusnya
                for (int i = 0; i < masterPengaduanList.size(); i++) {
                    if (masterPengaduanList.get(i).getId() == pengaduan.getId()) {
                        masterPengaduanList.get(i).setStatus(newStatus);
                        break;
                    }
                }
                showAlert("Sukses", "Status pengaduan berhasil diubah menjadi: " + newStatus);
                // Setelah update, filter ulang tabel jika ada filter aktif
                filterTable(table, statusFilterCombo.getValue()); // Panggil filterTable dengan table
            } else {
                showAlert("Gagal", "Gagal mengubah status pengaduan.");
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}