package org.kelompok20.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.kelompok20.controller.AuthController;
import org.kelompok20.model.User; // Import kelas User

import java.util.Optional;

public class LoginView extends Application {

    private AuthController authController = new AuthController(); // Instance AuthController

    @Override
    public void start(Stage primaryStage) {
        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Komponen
        Label titleLabel = new Label("Login Sistem Pengaduan Masyarakat");
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
                Optional<User> loggedInUser = authController.login(username, password, role);

                if (loggedInUser.isPresent()) {
                    primaryStage.close(); // Tutup jendela login

                    if (role.equals("Admin")) {
                        // Buka Admin Dashboard
                        new AdminDashboardView(loggedInUser.get()).start(new Stage());
                    } else if (role.equals("Warga")) {
                        // Buka Warga Dashboard
                        new WargaDashboardView(loggedInUser.get()).start(new Stage());
                    }
                } else {
                    showAlert("Login Gagal", "Username, Password, atau Role tidak cocok!");
                }
            }
        });

        // Action untuk register
        registerButton.setOnAction(e -> {
            // Contoh implementasi registrasi sederhana
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Registrasi Pengguna Baru");
            dialog.setHeaderText("Masukkan detail untuk registrasi");
            dialog.setContentText("Username:");

            Optional<String> resultUsername = dialog.showAndWait();
            if (resultUsername.isPresent() && !resultUsername.get().isEmpty()) {
                PasswordField newPasswordField = new PasswordField();
                newPasswordField.setPromptText("Password");

                Dialog<String> passwordDialog = new Dialog<>();
                passwordDialog.setTitle("Registrasi");
                passwordDialog.setHeaderText("Masukkan password untuk " + resultUsername.get());
                ButtonType registerButtonType = new ButtonType("Daftar", ButtonBar.ButtonData.OK_DONE);
                passwordDialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);
                passwordDialog.getDialogPane().setContent(newPasswordField);

                passwordDialog.setResultConverter(dialogButton -> {
                    if (dialogButton == registerButtonType) {
                        return newPasswordField.getText();
                    }
                    return null;
                });

                Optional<String> resultPassword = passwordDialog.showAndWait();
                resultPassword.ifPresent(newPassword -> {
                    if (newPassword.isEmpty()) {
                        showAlert("Error", "Password tidak boleh kosong!");
                    } else {
                        // Default role untuk registrasi adalah Warga
                        if (authController.register(resultUsername.get(), newPassword, "Warga")) {
                            showAlert("Sukses", "Registrasi berhasil! Silakan login.");
                        } else {
                            showAlert("Gagal", "Username sudah digunakan. Silakan pilih username lain.");
                        }
                    }
                });
            }
        });

        // Scene dan Stage
        Scene scene = new Scene(grid, 380, 250); // Ukuran disesuaikan
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sistem Pengaduan - Login");
        primaryStage.setResizable(false); // Nonaktifkan resize
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