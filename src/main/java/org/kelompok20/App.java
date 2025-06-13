package org.kelompok20;

import javafx.application.Application;
import javafx.stage.Stage;
import org.kelompok20.view.LoginView;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        LoginView loginView = new LoginView();
        loginView.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}