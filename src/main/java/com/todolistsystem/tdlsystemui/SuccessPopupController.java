package com.todolistsystem.tdlsystemui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SuccessPopupController {
    @FXML private Label labelSuccessMessage;

    public void setMessage(String message) {
        labelSuccessMessage.setText(message);
    }

    @FXML
    private void closePopup() {
        Stage stage = (Stage) labelSuccessMessage.getScene().getWindow();
        stage.close();
    }
}
