package com.todolistsystem.tdlsystemui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/todolistsystem/tdlsystemui/MainView.fxml")); // Main window
        Scene scene = new Scene(fxmlLoader.load(), 690, 500); // Default window size
        stage.setTitle("To-Do List Manager"); // Title of program
        stage.setScene(scene);
        stage.setMinWidth(690);  // Prevents window from shrinking too much
        stage.setMinHeight(500); // Same
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}