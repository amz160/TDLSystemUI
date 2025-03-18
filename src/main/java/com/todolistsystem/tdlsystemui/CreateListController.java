package com.todolistsystem.tdlsystemui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateListController {
    @FXML private TextField textFieldListName; // Input field for new to-do list name

    private final ToDoListDAO listDAO = new ToDoListDAO();

    // Handles creating a new to-do list
    @FXML
    private void createList() {
        String listName = textFieldListName.getText().trim();
        if (listName.isEmpty()) {
            AlertManager.showError("List name cannot be empty.");
            return;
        }
        listDAO.createList(listName);
        AlertManager.showSuccess("List created successfully.");
        closePopup();
    }

    // Closes the create list pop-up
    @FXML
    private void closePopup() {
        Stage stage = (Stage) textFieldListName.getScene().getWindow();
        stage.close();
    }
}