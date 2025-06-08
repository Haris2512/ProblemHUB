package org.kelompok20.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginView extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Komponen
        Label titleLabel = new Label("Login Pengaduan Masyarakat");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        grid.add(titleLabel, 0, 0, 2, 1);

        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        Label roleLabel = new Label("Role:");
        grid.add(roleLabel, 0, 3);
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Warga", "Admin");
        roleCombo.setValue("Warga");
        grid.add(roleCombo, 1, 3);

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        grid.add(loginButton, 0, 4);
        grid.add(registerButton, 1, 4);

        // Action untuk login
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleCombo.getValue();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Username dan Password harus diisi!");
            } else {
                showAlert("Info", "Login: " + username + ", Role: " + role);
                // Nanti panggil AuthController
            }
        });

        // Action untuk register
        registerButton.setOnAction(e -> showAlert("Info", "Fitur Register belum diimplementasikan."));

        // Scene dan Stage
        Scene scene = new Scene(grid, 350, 220);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sistem Pengaduan - Login");
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