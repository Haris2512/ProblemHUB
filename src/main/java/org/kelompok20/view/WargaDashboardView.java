package org.kelompok20.view;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WargaDashboardView extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        Label titleLabel = new Label("Dashboard Warga");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        borderPane.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        TableView<String[]> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(
            new String[]{"1", "Jalan Rusak", "Jl. Merdeka", "Diproses"},
            new String[]{"2", "Lampu Mati", "Jl. Sudirman", "Selesai"}
        ));

        TableColumn<String[], String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));
        TableColumn<String[], String> kategoriCol = new TableColumn<>("Kategori");
        kategoriCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
        TableColumn<String[], String> lokasiCol = new TableColumn<>("Lokasi");
        lokasiCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));
        TableColumn<String[], String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[3]));

        table.getColumns().addAll(idCol, kategoriCol, lokasiCol, statusCol);

        borderPane.setCenter(table);

        HBox buttonBox = new HBox(10);
        Button newPengaduanButton = new Button("Buat Pengaduan Baru");
        Button logoutButton = new Button("Logout");
        buttonBox.getChildren().addAll(newPengaduanButton, logoutButton);
        borderPane.setBottom(buttonBox);

        newPengaduanButton.setOnAction(e -> new FormPengaduanView().start(new Stage()));
        logoutButton.setOnAction(e -> {
            primaryStage.close();
            new LoginView().start(new Stage());
        });

        Scene scene = new Scene(borderPane, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard Warga");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}