package com.todolistsystem.tdlsystemui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainController {
    @FXML
    private TableView<Task> tableViewTasks;
    @FXML
    private TableColumn<Task, String> columnDescription;
    @FXML
    private TableColumn<Task, String> columnDueDate;
    @FXML
    private TableColumn<Task, Boolean> columnCompleted;
    @FXML
    private Label labelSelectedList;
    @FXML
    private ComboBox<String> comboBoxToDoLists;
    @FXML
    private TableColumn<Task, Void> columnDelete;


    private final ToDoListDAO listDAO = new ToDoListDAO();
    private final TaskDAO taskDAO = new TaskDAO();
    private int selectedListID = -1; // Stores the currently selected list

    @FXML
    public void initialize() {
        loadToDoLists();
        setupTableColumns();

        // Automatically select the most recent list on startup
        String recentList = getMostRecentList();
        if (recentList != null) {
            comboBoxToDoLists.setValue(recentList);
            selectedListID = getListIDByName(recentList);
            labelSelectedList.setText("Tasks for: " + recentList);
            loadTasks(selectedListID);
        }

        // Sets up the Delete button column
        columnDelete.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.getStyleClass().add("table-delete-button");

                deleteButton.setOnAction(event -> {
                    Task task = (Task) getTableView().getItems().get(getIndex()); // Gets task
                    PopupManager.showConfirmationPopup("Are you sure you want to delete this task?", () -> {
                        taskDAO.deleteTask(task.getTaskID()); // Deletes task from DB
                        loadTasks(selectedListID); // Refreshes to-do task list
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    public ComboBox<String> getComboBoxToDoLists() {
        return comboBoxToDoLists;
    }

    // Gets listID from database based on name
    public int getListIDByName(String listName) {
        String sql = "SELECT listID FROM ToDoLists WHERE listName = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, listName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("listID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no valid listID is found
    }

    // Gets most recently created to-do list
    private String getMostRecentList() {
        String sql = "SELECT listName FROM ToDoLists ORDER BY createdAt DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("listName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No lists found
    }

    // Sets up table and how everything is shown
    private void setupTableColumns() {
        columnDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        columnDueDate.setCellValueFactory(cellData -> cellData.getValue().dueDateProperty().asString());
        columnCompleted.setCellValueFactory(cellData -> cellData.getValue().completedProperty().asObject());

        // Task completion column
        columnCompleted.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean completed, boolean empty) {
                super.updateItem(completed, empty);
                if (empty || completed == null) {
                    setText(null);
                } else {
                    setText(completed ? "Complete" : "Incomplete"); // Converts true/false to complete/incomplete
                }
            }
        });
    }

    // To-do list dropdown menu
    public void loadToDoLists() {
        List<String> lists = listDAO.getAllLists(); // Gets lists from DB
        ObservableList<String> observableLists = FXCollections.observableArrayList(lists);
        comboBoxToDoLists.setItems(observableLists); // Populates dropdown
    }

    // Loads tasks for the selected list
    private void loadTasks(int listID) {
        List<Task> tasks = taskDAO.getTasksByList(listID);
        ObservableList<Task> observableTasks = FXCollections.observableArrayList(tasks);
        tableViewTasks.setItems(observableTasks);
    }

    // Opens create new list pop-up
    @FXML
    private void showCreateListPopup() {
        PopupManager.showPopup("CreateListView.fxml", "Create New List");
        loadToDoLists(); // List refreshes after adding
    }

    // Opens add task pop-up
    @FXML
    private void showAddTaskPopup() {
        if (selectedListID == -1) {
            PopupManager.showErrorPopup("Please select a to-do list first.");
            return;
        }
        PopupManager.showPopup("AddTaskView.fxml", "Add Task", selectedListID);
        loadTasks(selectedListID);
    }

    // Opens edit task pop-up
    @FXML
    private void showEditTaskPopup() {
        Task selectedTask = tableViewTasks.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            PopupManager.showErrorPopup("Please select a task to edit.");
            return;
        }
        PopupManager.showPopup("EditTaskView.fxml", "Edit Task", selectedTask);
        loadTasks(selectedListID);
    }

    // Confirmation pop-up before deleting tasks
    @FXML
    private void showDeleteConfirmation() {
        Task selectedTask = tableViewTasks.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            PopupManager.showErrorPopup("Please select a task to delete.");
            return;
        }
        boolean confirmed = AlertManager.showConfirmation("Are you sure you want to delete this task?");
        if (confirmed) {
            taskDAO.deleteTask(selectedTask.getTaskID());
            loadTasks(selectedListID);
        }
    }

    // Drop down list selection and loading tasks
    @FXML
    private void handleListSelection() {
        String selectedList = comboBoxToDoLists.getValue();
        if (selectedList != null) {
            selectedListID = getListIDByName(selectedList);
            System.out.println("Selected list: " + selectedList + " (ID: " + selectedListID + ")");
            labelSelectedList.setText("Tasks for: " + selectedList);
            loadTasks(selectedListID);
        }
    }

    // Confirmation before deleting to-do list
    @FXML
    private void showDeleteListConfirmation() {
        if (selectedListID == -1) {
            PopupManager.showErrorPopup("Please select a to-do list to delete.");
            return;
        }

        PopupManager.showConfirmationPopup("Are you sure you want to delete this to-do list?", () -> {
            listDAO.deleteList(selectedListID);
            loadToDoLists(); // Refresh the dropdown after deleting
            selectedListID = -1;
            labelSelectedList.setText("Tasks for: [Select a List]");
            tableViewTasks.getItems().clear(); // Clear the task table since the list is gone
        });
    }
}