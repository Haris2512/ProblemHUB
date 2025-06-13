package org.kelompok20.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import org.kelompok20.controller.PengaduanController;
import org.kelompok20.controller.AuthController;
import org.kelompok20.model.Pengaduan;
import org.kelompok20.model.User;
import org.kelompok20.utils.FileHandler;
import org.kelompok20.service.IPengaduanService;

import java.io.File;

public class WargaDashboardView extends Application {

    private IPengaduanService pengaduanService = new PengaduanController();
    private AuthController authController = new AuthController();
    private FileHandler fileHandler = new FileHandler();
    private User currentUser;
    private TableView<Pengaduan> table;

    public WargaDashboardView(User user) {
        this.currentUser = user;
    }

    public WargaDashboardView() {
        this.currentUser = new User("warga_test", "password_test", "Warga");
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        borderPane.getStyleClass().add("root");

        Label titleLabel = new Label("Dashboard Warga");
        titleLabel.getStyleClass().add("title-label");
        borderPane.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        table = new TableView<>();
        table.getStyleClass().add("table-view");

        if (currentUser != null) {
            refreshTable();
        } else {
            table.setItems(FXCollections.emptyObservableList());
            showAlert("Peringatan", "Tidak ada pengguna yang login. Menampilkan pengaduan kosong.");
        }

        TableColumn<Pengaduan, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        idCol.setPrefWidth(40);

        TableColumn<Pengaduan, String> kategoriCol = new TableColumn<>("Kategori");
        kategoriCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKategori()));
        kategoriCol.setPrefWidth(100);

        TableColumn<Pengaduan, String> lokasiCol = new TableColumn<>("Lokasi");
        lokasiCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLokasi()));
        lokasiCol.setPrefWidth(120);

        TableColumn<Pengaduan, String> deskripsiCol = new TableColumn<>("Deskripsi");
        deskripsiCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDeskripsi()));
        deskripsiCol.setPrefWidth(180);

        TableColumn<Pengaduan, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));
        statusCol.setPrefWidth(90);

        TableColumn<Pengaduan, Void> fotoCol = new TableColumn<>("Foto");
        fotoCol.setCellFactory(param -> new TableCell<Pengaduan, Void>() {
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
                if (empty) {
                    setGraphic(null);
                } else {
                    if (getTableView().getItems().get(getIndex()).getFotoPath() != null && !getTableView().getItems().get(getIndex()).getFotoPath().isEmpty()) {
                        setGraphic(btn);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });
        fotoCol.setPrefWidth(80);

        table.getColumns().addAll(idCol, kategoriCol, lokasiCol, deskripsiCol, statusCol, fotoCol);

        borderPane.setCenter(table);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button newPengaduanButton = new Button("Buat Pengaduan Baru");
        newPengaduanButton.getStyleClass().add("button");
        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("button-cancel");
        buttonBox.getChildren().addAll(newPengaduanButton, logoutButton);
        borderPane.setBottom(buttonBox);

        newPengaduanButton.setOnAction(e -> {
            String usernameToPass = (currentUser != null) ? currentUser.getUsername() : "Anonim";
            Stage formStage = new Stage();
            FormPengaduanView formView = new FormPengaduanView(usernameToPass);
            formView.start(formStage);

            formStage.setOnHiding(event -> {
                refreshTable();
            });
        });

        logoutButton.setOnAction(e -> {
            authController.logout();
            primaryStage.close();
            new LoginView().start(new Stage());
        });

        Scene scene = new Scene(borderPane, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard Warga");
        primaryStage.show();
    }

    private void refreshTable() {
        if (currentUser != null) {
            ObservableList<Pengaduan> userPengaduans = FXCollections.observableArrayList(
                pengaduanService.getPengaduanByUsername(currentUser.getUsername())
            );
            table.setItems(userPengaduans);
            table.refresh();
        }
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
}
