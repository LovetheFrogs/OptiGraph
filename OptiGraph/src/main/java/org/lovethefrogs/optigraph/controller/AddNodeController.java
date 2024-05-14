package org.lovethefrogs.optigraph.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AddNodeController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}