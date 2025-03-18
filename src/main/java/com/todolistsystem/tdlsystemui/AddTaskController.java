package com.todolistsystem.tdlsystemui;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Date;

public class AddTaskController {
    @FXML private TextField textFieldTaskDescription; // Input field for task description
    @FXML private DatePicker datePickerDueDate; // Date picker for selecting due date
    private int listID; // Selected to-do list's ID

    private final TaskDAO taskDAO = new TaskDAO();

    // Sets the listID when opening add task pop-up
    public void setListID(int listID) {
        this.listID = listID;
    }

    // Handles adding a new task
    @FXML
    private void addTask() {
        String description = textFieldTaskDescription.getText().trim();
        if (description.isEmpty()) {
            AlertManager.showError("Task description cannot be empty.");
            return;
        }
        // Gets the due date if selected
        Date dueDate = datePickerDueDate.getValue() != null ? Date.valueOf(datePickerDueDate.getValue()) : null;
        taskDAO.addTask(listID, description, dueDate);
        AlertManager.showSuccess("Task added successfully.");
        closePopup();
    }

    // Closes add task pop-up
    @FXML
    private void closePopup() {
        Stage stage = (Stage) textFieldTaskDescription.getScene().getWindow();
        stage.close();
    }
}
