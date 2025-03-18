package com.todolistsystem.tdlsystemui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Date;

public class EditTaskController {
    @FXML private TextField textFieldTaskDescription; // Input field for task description
    @FXML private DatePicker datePickerDueDate; // Date picker for task due date
    @FXML private CheckBox checkBoxCompleted; // Checkbox for marking tasks as complete
    private Task task; // Task being edited

    private final TaskDAO taskDAO = new TaskDAO();

    // Sets task details in the edit form
    public void setTask(Task task) {
        this.task = task;
        textFieldTaskDescription.setText(task.getDescription());
        datePickerDueDate.setValue(task.getDueDate().toLocalDate());
        checkBoxCompleted.setSelected(task.isCompleted());
    }

    // Updates task description
    @FXML
    private void updateTask() {
        String description = textFieldTaskDescription.getText().trim();
        if (description.isEmpty()) {
            AlertManager.showError("Task description cannot be empty.");
            return;
        }
        // Get the due date if selected
        Date dueDate = datePickerDueDate.getValue() != null ? Date.valueOf(datePickerDueDate.getValue()) : null;
        boolean isCompleted = checkBoxCompleted.isSelected();

        taskDAO.updateTask(task.getTaskID(), description, dueDate, isCompleted);
        AlertManager.showSuccess("Task updated successfully.");
        closePopup();
    }

    // Closes the edit task pop-up
    @FXML
    private void closePopup() {
        Stage stage = (Stage) textFieldTaskDescription.getScene().getWindow();
        stage.close();
    }
}