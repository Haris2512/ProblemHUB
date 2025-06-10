package org.kelompok20.view;

import javafx.application.Application;
import javafx.collections.FXCollections; // Ditambahkan
import javafx.geometry.Insets;
import javafx.geometry.Pos; // Ditambahkan
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox; // Ditambahkan untuk layout TOP
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty; // Ditambahkan, meskipun tidak wajib, ini praktik yang baik

public class AdminDashboardView extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        Label titleLabel = new Label("Dashboard Admin");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        HBox filterBox = new HBox(10);
        filterBox.getChildren().addAll(new Label("Filter Status:"), new ComboBox<>(FXCollections.observableArrayList("Semua", "Belum Diproses", "Diproses", "Selesai")));

        // Menggabungkan titleLabel dan filterBox dalam VBox untuk bagian TOP
        VBox topContainer = new VBox(10); // Spasi vertikal 10px
        topContainer.getChildren().addAll(titleLabel, filterBox);
        topContainer.setAlignment(Pos.CENTER); // Pusatkan konten di VBox

        borderPane.setTop(topContainer); // Set VBox sebagai bagian TOP

        TableView<String[]> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(
            new String[]{"1", "Jalan Rusak", "Jl. Merdeka", "Belum Diproses", "Ubah Status"},
            new String[]{"2", "Lampu Mati", "Jl. Sudirman", "Selesai", "Ubah Status"}
        ));

        TableColumn<String[], String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        TableColumn<String[], String> kategoriCol = new TableColumn<>("Kategori");
        kategoriCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        TableColumn<String[], String> lokasiCol = new TableColumn<>("Lokasi");
        lokasiCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        TableColumn<String[], String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
        TableColumn<String[], String> aksiCol = new TableColumn<>("Aksi");
        aksiCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));

        table.getColumns().addAll(idCol, kategoriCol, lokasiCol, statusCol, aksiCol);

        borderPane.setCenter(table);

        Button logoutButton = new Button("Logout");
        BorderPane.setAlignment(logoutButton, Pos.BOTTOM_RIGHT); // Pusatkan tombol logout di kanan bawah
        borderPane.setBottom(logoutButton);


        logoutButton.setOnAction(e -> {
            primaryStage.close();
            new LoginView().start(new Stage());
        });

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard Admin");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}