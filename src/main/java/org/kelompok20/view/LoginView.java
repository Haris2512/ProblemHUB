package org.kelompok20.view;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kelompok20.controller.AuthController;
import org.kelompok20.model.User;

import java.util.Optional;

public class LoginView extends Application {

    private AuthController authController = new AuthController();

    @Override
    public void start(Stage primaryStage) {
        double sceneWidth = 400;
        double sceneHeight = 400;

        Image logo = new Image(getClass().getResourceAsStream("/images/logo.png"));
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(sceneWidth);
        logoView.setFitHeight(sceneHeight);
        logoView.setPreserveRatio(false);

        StackPane splashPane = new StackPane(logoView);
        splashPane.setStyle("-fx-background-color: white;");

        Scene splashScene = new Scene(splashPane, sceneWidth, sceneHeight);
        primaryStage.setScene(splashScene);
        primaryStage.setTitle("LaporHub");
        primaryStage.setResizable(false);
        primaryStage.show();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), logoView);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        PauseTransition pause = new PauseTransition(Duration.seconds(3));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), logoView);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> showLoginForm(primaryStage));

        fadeIn.setOnFinished(e -> pause.play());
        pause.setOnFinished(e -> fadeOut.play());
    }

    private void showLoginForm(Stage primaryStage) {
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
        usernameField.setPromptText("Nama lengkap");
        usernameField.setTooltip(new Tooltip("Masukkan nama lengkap Anda"));
        usernameField.getStyleClass().add("text-field");
        grid.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("text-field");
        grid.add(passwordField, 1, 2);

        CheckBox showPasswordCheck = new CheckBox("Tampilkan Password");
        TextField showPasswordField = new TextField();
        showPasswordField.setManaged(false);
        showPasswordField.setVisible(false);
        showPasswordField.getStyleClass().add("text-field");

        showPasswordCheck.setOnAction(e -> {
            if (showPasswordCheck.isSelected()) {
                showPasswordField.setText(passwordField.getText());
                showPasswordField.setManaged(true);
                showPasswordField.setVisible(true);
                passwordField.setManaged(false);
                passwordField.setVisible(false);
            } else {
                passwordField.setText(showPasswordField.getText());
                passwordField.setManaged(true);
                passwordField.setVisible(true);
                showPasswordField.setManaged(false);
                showPasswordField.setVisible(false);
            }
        });

        passwordField.textProperty().addListener((obs, oldText, newText) -> showPasswordField.setText(newText));
        showPasswordField.textProperty().addListener((obs, oldText, newText) -> passwordField.setText(newText));

        grid.add(showPasswordField, 1, 2);
        grid.add(showPasswordCheck, 1, 3);

        Label roleLabel = new Label("Role:");
        grid.add(roleLabel, 0, 4);
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Warga", "Admin");
        roleCombo.setValue("Warga");
        roleCombo.getStyleClass().add("combo-box");
        grid.add(roleCombo, 1, 4);

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button");
        Button registerButton = new Button("Register");
        registerButton.getStyleClass().add("button");
        grid.add(loginButton, 0, 5);
        grid.add(registerButton, 1, 5);

        passwordField.setOnAction(e -> loginButton.fire());
        showPasswordField.setOnAction(e -> loginButton.fire());

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.isVisible() ? passwordField.getText() : showPasswordField.getText();
            String role = roleCombo.getValue();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Username dan Password harus diisi!");
            } else {
                Optional<User> loggedInUser = authController.login(username, password, role);
                if (loggedInUser.isPresent()) {
                    primaryStage.close();
                    if (role.equals("Admin")) {
                        new AdminDashboardView(loggedInUser.get()).start(new Stage());
                    } else {
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
                    } else if (newPassword.length() < 5 || !newPassword.matches(".*[a-zA-Z].*") || !newPassword.matches(".*\\d.*")) {
                        showAlert("Error", "Password harus minimal 5 karakter, mengandung huruf dan angka!");
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

        Scene loginScene = new Scene(grid, 400, 400);
        loginScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(loginScene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}