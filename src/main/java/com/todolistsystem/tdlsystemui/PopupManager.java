package com.todolistsystem.tdlsystemui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class PopupManager {

    // Opens pop-up window
    public static void showPopup(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(PopupManager.class.getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Blocks main window
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            AlertManager.showError("Failed to load " + title + ".");
        }
    }

    // Opens add task pop-up, passes the listID to assign the task to the correct list
    public static void showPopup(String fxmlFile, String title, int listID) {
        try {
            FXMLLoader loader = new FXMLLoader(PopupManager.class.getResource(fxmlFile));
            Parent root = loader.load();

            // Pass listID to AddTaskController
            AddTaskController controller = loader.getController();
            controller.setListID(listID);

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Blocks main window
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            AlertManager.showError("Failed to load " + title + ".");
        }
    }

    // Opens edit task pop-up, passing the selected task for editing
    public static void showPopup(String fxmlFile, String title, Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(PopupManager.class.getResource(fxmlFile));
            Parent root = loader.load();

            // Pass task object to EditTaskController
            EditTaskController controller = loader.getController();
            controller.setTask(task);

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Blocks main window
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            AlertManager.showError("Failed to load " + title + ".");
        }
    }

    // Opens confirmation pop-up
    public static void showConfirmationPopup(String message, Runnable onConfirm) {
        try {
            FXMLLoader loader = new FXMLLoader(PopupManager.class.getResource("ConfirmationView.fxml"));
            Parent root = loader.load();

            ConfirmationController controller = loader.getController();
            controller.setOnConfirm(onConfirm); // Pass the action to execute on confirmation

            Stage stage = new Stage();
            stage.setTitle("Confirmation");
            stage.setScene(new Scene(root, 300, 150));
            stage.initModality(Modality.APPLICATION_MODAL); // Blocks main window
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            AlertManager.showError("Failed to load confirmation pop-up.");
        }
    }
}
