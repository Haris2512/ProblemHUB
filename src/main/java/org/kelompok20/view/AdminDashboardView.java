package org.kelompok20.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import org.kelompok20.controller.PengaduanController;
import org.kelompok20.controller.AuthController;
import org.kelompok20.model.Pengaduan;
import org.kelompok20.model.User;
import org.kelompok20.utils.FileHandler;
import org.kelompok20.service.IPengaduanService;

import java.io.File;
import java.util.Optional;

public class AdminDashboardView extends Application {

    private IPengaduanService pengaduanService = new PengaduanController();
    private AuthController authController = new AuthController();
    private FileHandler fileHandler = new FileHandler();
    private ObservableList<Pengaduan> masterPengaduanList;
    private User currentUser;
    private ComboBox<String> statusFilterCombo;
    private TableView<Pengaduan> table;
    private Pagination pagination;
    private static final int ROWS_PER_PAGE = 10;

    public AdminDashboardView(User user) {
        this.currentUser = user;
    }

    public AdminDashboardView() {
        this.currentUser = new User("admin_test", "password_test", "Admin");
    }

    @Override
    public void start(Stage primaryStage) {
        masterPengaduanList = FXCollections.observableArrayList(pengaduanService.getAllPengaduan());

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        borderPane.getStyleClass().add("root");

        Label titleLabel = new Label("Dashboard Admin");
        titleLabel.getStyleClass().add("title-label");

        HBox filterBox = new HBox(5);
        filterBox.setAlignment(Pos.CENTER_LEFT);
        statusFilterCombo = new ComboBox<>(
                FXCollections.observableArrayList("Semua", "Belum Diproses", "Diproses", "Selesai"));
        statusFilterCombo.setValue("Semua");
        statusFilterCombo.getStyleClass().add("combo-box");
        filterBox.getChildren().addAll(new Label("Filter Status:"), statusFilterCombo);

        VBox topContainer = new VBox(5);
        topContainer.getChildren().addAll(titleLabel, filterBox);
        topContainer.setAlignment(Pos.CENTER);
        borderPane.setTop(topContainer);

        table = new TableView<>();
        table.getStyleClass().add("table-view");

        TableColumn<Pengaduan, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        idCol.setPrefWidth(40);

        TableColumn<Pengaduan, String> kategoriCol = new TableColumn<>("Kategori");
        kategoriCol.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKategori()));
        kategoriCol.setPrefWidth(90);

        TableColumn<Pengaduan, String> lokasiCol = new TableColumn<>("Lokasi");
        lokasiCol.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLokasi()));
        lokasiCol.setPrefWidth(120);

        TableColumn<Pengaduan, String> deskripsiCol = new TableColumn<>("Deskripsi");
        deskripsiCol.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDeskripsi()));
        deskripsiCol.setPrefWidth(180);

        TableColumn<Pengaduan, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));
        statusCol.setPrefWidth(90);

        TableColumn<Pengaduan, String> pelaporCol = new TableColumn<>("Pelapor");
        pelaporCol.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPelaporUsername()));
        pelaporCol.setPrefWidth(80);

        TableColumn<Pengaduan, Void> aksiCol = new TableColumn<>("Aksi");
        aksiCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Ubah");
            {
                btn.getStyleClass().add("button");
                btn.setPrefWidth(70);
                btn.setOnAction(event -> {
                    Pengaduan pengaduan = getTableView().getItems().get(getIndex());
                    showStatusChangeDialog(pengaduan);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        aksiCol.setPrefWidth(80);

        TableColumn<Pengaduan, Void> fotoCol = new TableColumn<>("Foto");
        fotoCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Lihat");
            {
                btn.getStyleClass().add("button");
                btn.setPrefWidth(70);
                btn.setOnAction(event -> {
                    Pengaduan pengaduan = getTableView().getItems().get(getIndex());
                    if (pengaduan.getFotoPath() != null && !pengaduan.getFotoPath().isEmpty()) {
                        showImageDialog(pengaduan.getFotoPath());
                    } else {
                        showAlert("Info", "Tidak ada foto untuk pengaduan ini.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableView().getItems().get(getIndex()).getFotoPath() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
        fotoCol.setPrefWidth(80);

        table.getColumns().addAll(idCol, kategoriCol, lokasiCol, deskripsiCol, statusCol, pelaporCol, aksiCol, fotoCol);

        pagination = new Pagination((int) Math.ceil((double) masterPengaduanList.size() / ROWS_PER_PAGE), 0);
        pagination.setPageFactory(this::createPage);

        VBox centerContent = new VBox(10, pagination);
        centerContent.setAlignment(Pos.CENTER);
        borderPane.setCenter(centerContent);

        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("button-cancel");
        BorderPane.setAlignment(logoutButton, Pos.BOTTOM_RIGHT);
        borderPane.setBottom(logoutButton);

        statusFilterCombo.setOnAction(e -> refreshTable());

        logoutButton.setOnAction(e -> {
            authController.logout();
            primaryStage.close();
            new LoginView().start(new Stage());
        });

        Scene scene = new Scene(borderPane, 850, 450);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard Admin");

        primaryStage.setResizable(false);

        primaryStage.show();
    }

    private void refreshTable() {
        masterPengaduanList.setAll(pengaduanService.getAllPengaduan());

        String currentStatusFilter = statusFilterCombo.getValue();
        if (!currentStatusFilter.equals("Semua")) {
            masterPengaduanList.removeIf(p -> !p.getStatus().equals(currentStatusFilter));
        }

        pagination.setPageCount((int) Math.ceil((double) masterPengaduanList.size() / ROWS_PER_PAGE));
        pagination.setCurrentPageIndex(0);
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, masterPengaduanList.size());
        table.setItems(FXCollections.observableArrayList(masterPengaduanList.subList(fromIndex, toIndex)));
        return new BorderPane(table);
    }

    private void showStatusChangeDialog(Pengaduan pengaduan) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Ubah Status Pengaduan");
        dialog.setHeaderText("Pengaduan ID: " + pengaduan.getId() + "\n" +
                "Kategori: " + pengaduan.getKategori() + "\n" +
                "Lokasi: " + pengaduan.getLokasi());
        dialog.getDialogPane().getStyleClass().add("dialog-pane");

        ButtonType applyButtonType = new ButtonType("Terapkan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

        ComboBox<String> statusComboBox = new ComboBox<>(
                FXCollections.observableArrayList("Belum Diproses", "Diproses", "Selesai"));
        statusComboBox.setValue(pengaduan.getStatus());
        statusComboBox.getStyleClass().add("combo-box");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        grid.add(new Label("Status Baru:"), 0, 0);
        grid.add(statusComboBox, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyButtonType) {
                return statusComboBox.getValue();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();


       result.ifPresent(newStatus -> {
            if (pengaduanController.updatePengaduanStatus(pengaduan.getId(), newStatus)) {
            // Perbarui ObservableList
                for (int i = 0; i < masterPengaduanList.size(); i++) {
                    if (masterPengaduanList.get(i).getId() == pengaduan.getId()) {
                        masterPengaduanList.get(i).setStatus(newStatus);
                break;
            }
        }
        showAlert("Sukses", "Status pengaduan berhasil diubah menjadi: " + newStatus);
        
        // Filter ulang jika ada filter aktif
        filterTable(table, statusFilterCombo.getValue());

        // Refresh tabel untuk menampilkan perubahan
        table.refresh();

    } else {
        showAlert("Gagal", "Gagal mengubah status pengaduan.");
    }
});


        result.ifPresent(newStatus -> {
            if (pengaduanService.updatePengaduanStatus(pengaduan.getId(), newStatus)) {
                showAlert("Sukses", "Status pengaduan berhasil diubah menjadi: " + newStatus);
                refreshTable();
            } else {
                showAlert("Gagal", "Gagal mengubah status pengaduan.");
            }
        });

    }

    private void showImageDialog(String imagePath) {
        Stage imageStage = new Stage();
        BorderPane imagePane = new BorderPane();
        ImageView imageView = new ImageView();
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);

        File imageFile = fileHandler.getFile(imagePath);
        if (imageFile != null && imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            imageView.setImage(image);
        } else {
            showAlert("Error", "File gambar tidak ditemukan: " + imagePath);
            return;
        }

        imagePane.setCenter(imageView);
        Scene imageScene = new Scene(imagePane, 420, 420);
        imageStage.setScene(imageScene);
        imageStage.setTitle("Foto Pengaduan");
        imageStage.show();
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