package com.todolistsystem.tdlsystemui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmationController {

    private Runnable onConfirm;

    @FXML
    private Label labelConfirmationText;

    // Action executes when user confirms
    public void setOnConfirm(Runnable onConfirm) {
        this.onConfirm = onConfirm;
    }

    // Executes and closes pop-up when yes is clicked
    @FXML
    private void confirmAction() {
        if (onConfirm != null) {
            onConfirm.run();
        }
        closePopup();
    }

    // Closes pop-up when no is clicked
    @FXML
    private void closePopup() {
        Stage stage = (Stage) labelConfirmationText.getScene().getWindow();
        stage.close();
    }
}