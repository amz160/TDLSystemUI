package com.todolistsystem.tdlsystemui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class ManageTasksController {
    @FXML private TableView<Task> tableViewTasks; // Table displaying tasks
    @FXML private TableColumn<Task, String> columnDescription;
    @FXML private TableColumn<Task, String> columnDueDate;
    @FXML private TableColumn<Task, Boolean> columnCompleted;
    private int selectedListID = -1; // Currently selected listID

    private final TaskDAO taskDAO = new TaskDAO();

    // Initializes table columns
    @FXML
    public void initialize() {
        setupTableColumns();
    }

    // Sets up table columns to display task details
    private void setupTableColumns() {
        columnDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        columnDueDate.setCellValueFactory(cellData -> cellData.getValue().dueDateProperty().asString());
        columnCompleted.setCellValueFactory(cellData -> cellData.getValue().completedProperty().asObject());
    }

    // Sets the listID and loads tasks
    public void setListID(int listID) {
        this.selectedListID = listID;
        loadTasks();
    }

    // Loads tasks for the selected list
    private void loadTasks() {
        if (selectedListID == -1) return;

        List<Task> tasks = taskDAO.getTasksByList(selectedListID);
        ObservableList<Task> observableTasks = FXCollections.observableArrayList(tasks);
        tableViewTasks.setItems(observableTasks);
    }

    // Deletes the selected task after confirming
    @FXML
    private void deleteSelectedTask() {
        Task selectedTask = tableViewTasks.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            AlertManager.showError("Please select a task to delete.");
            return;
        }

        boolean confirmed = AlertManager.showConfirmation("Are you sure you want to delete this task?");
        if (confirmed) {
            taskDAO.deleteTask(selectedTask.getTaskID());
            loadTasks();
            AlertManager.showSuccess("Task deleted successfully.");
        }
    }
}
