package com.todolistsystem.tdlsystemui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorPopupController {
    @FXML private Label labelErrorMessage;

    public void setMessage(String message) {
        labelErrorMessage.setText(message);
    }

    @FXML
    private void closePopup() {
        Stage stage = (Stage) labelErrorMessage.getScene().getWindow();
        stage.close();
    }
}