package com.example.vehiclerentalsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    // These names must be EXACTLY what you typed in Scene Builder fx:id
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private RentalManager manager = new RentalManager();

    @FXML
    protected void onLoginButtonClick() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        User loggedInUser = manager.login(user, pass);

        if (loggedInUser != null) {
            errorLabel.setText("Login Success! Role: " + loggedInUser.getRole());
        } else {
            errorLabel.setText("Invalid username or password!");
        }
    }
}