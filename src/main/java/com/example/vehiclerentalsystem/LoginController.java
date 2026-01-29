package com.example.vehiclerentalsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel; // You can still use this or the Alert popups below

    private static RentalManager manager = new RentalManager();

    public static RentalManager getManager() {
        return manager;
    }

    @FXML
    protected void onLoginButtonClick() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        User loggedInUser = manager.validateUser(user, pass);

        if (loggedInUser != null) {
            try {
                String fxmlFile = "";
                String title = "";

                if (loggedInUser.getRole().equals("ADMIN")) {
                    fxmlFile = "admin-dashboard.fxml";
                    title = "Admin Dashboard";
                } else {
                    fxmlFile = "customer-view.fxml";
                    title = "Customer Dashboard";
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Scene scene = new Scene(loader.load(), 600, 650); // Increased height for the split tables
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle(title);
                stage.show();

            } catch (Exception e) {
                errorLabel.setText("Error loading screen!");
                e.printStackTrace();
            }
        } else {
            // UPDATED: Show feedback if username or password is wrong
            showErrorMessage("Invalid Credentials", "The username or password you entered is incorrect.");
        }
    }

    @FXML
    protected void onSignupButtonClick() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        // Validation: Fields cannot be empty
        if (user.isEmpty() || pass.isEmpty()) {
            showErrorMessage("Registration Error", "Please enter both a username and password.");
            return;
        }

        // register the new customer
        boolean success = manager.registerUser(user, pass);

        if (success) {
            showInfoMessage("Account Created", "Registration successful! You can now log in with your new account.");
            // Clear fields after signup
            usernameField.clear();
            passwordField.clear();
        } else {
            showErrorMessage("Registration Error", "This username is already taken. Please try another.");
        }
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfoMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}