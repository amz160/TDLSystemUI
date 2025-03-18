package com.todolistsystem.tdlsystemui;

import javafx.fxml.FXML;

public class ManageListsController {
    private final ToDoListDAO listDAO = new ToDoListDAO();

    @FXML
    private void deleteSelectedList() {
        MainController mainController = new MainController();
        String selectedList = mainController.getComboBoxToDoLists().getValue();

        if (selectedList == null) {
            AlertManager.showError("Please select a to-do list to delete.");
            return;
        }

        boolean confirmed = AlertManager.showConfirmation("Are you sure you want to delete this list?");
        if (confirmed) {
            int listID = mainController.getListIDByName(selectedList);
            listDAO.deleteList(listID);
            mainController.loadToDoLists(); // Refresh lists in dropdown
            AlertManager.showSuccess("List deleted successfully.");
        }
    }
}