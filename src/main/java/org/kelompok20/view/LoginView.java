package org.kelompok20.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.kelompok20.controller.AuthController;
import org.kelompok20.model.User;

import java.util.Optional;

public class LoginView extends Application {

    private AuthController authController = new AuthController();

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.getStyleClass().add("root");

        Label titleLabel = new Label("Sistem Pengaduan Masyarakat");
        titleLabel.getStyleClass().add("title-label");
        grid.add(titleLabel, 0, 0, 2, 1);

        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("text-field");
        grid.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("text-field");
        grid.add(passwordField, 1, 2);

        Label roleLabel = new Label("Role:");
        grid.add(roleLabel, 0, 3);
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Warga", "Admin");
        roleCombo.setValue("Warga");
        roleCombo.getStyleClass().add("combo-box");
        grid.add(roleCombo, 1, 3);

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button");
        Button registerButton = new Button("Register");
        registerButton.getStyleClass().add("button");
        grid.add(loginButton, 0, 4);
        grid.add(registerButton, 1, 4);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleCombo.getValue();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Username dan Password harus diisi!");
            } else {
                Optional<User> loggedInUser = authController.login(username, password, role);

                if (loggedInUser.isPresent()) {
                    primaryStage.close();

                    if (role.equals("Admin")) {
                        new AdminDashboardView(loggedInUser.get()).start(new Stage());
                    } else if (role.equals("Warga")) {
                        new WargaDashboardView(loggedInUser.get()).start(new Stage());
                    }
                } else {
                    showAlert("Login Gagal", "Username, Password, atau Role tidak cocok!");
                }
            }
        });

        registerButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Registrasi Pengguna Baru");
            dialog.setHeaderText("Masukkan detail untuk registrasi");
            dialog.setContentText("Username:");
            dialog.getDialogPane().getStyleClass().add("dialog-pane");

            Optional<String> resultUsername = dialog.showAndWait();
            if (resultUsername.isPresent() && !resultUsername.get().isEmpty()) {
                PasswordField newPasswordField = new PasswordField();
                newPasswordField.setPromptText("Password");
                newPasswordField.getStyleClass().add("text-field");

                Dialog<String> passwordDialog = new Dialog<>();
                passwordDialog.setTitle("Registrasi");
                passwordDialog.setHeaderText("Masukkan password untuk " + resultUsername.get());
                passwordDialog.getDialogPane().getStyleClass().add("dialog-pane");

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
                        if (authController.register(resultUsername.get(), newPassword, "Warga")) {
                            showAlert("Sukses", "Registrasi berhasil! Silakan login.");
                        } else {
                            showAlert("Gagal", "Registrasi gagal! Username '" + resultUsername.get() + "' sudah terdaftar.");
                        }
                    }
                });
            }
        });

        Scene scene = new Scene(grid, 350, 320);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
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
}
