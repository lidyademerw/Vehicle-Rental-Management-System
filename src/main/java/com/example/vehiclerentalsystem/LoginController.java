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
    @FXML private Label errorLabel;
    @FXML private TextField contactField;

    private static RentalManager manager = new RentalManager();

    public static RentalManager getManager() {
        return manager;
    }
    @FXML
    protected void onLoginButtonClick() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        // NEW: Get the text from the contact box (e.g., "999")
        String contact = contactField.getText();
        if (user.isEmpty() || pass.isEmpty() || contact.isEmpty()) {
            errorLabel.setText("Error: All fields (including Contact) are required!");
            return; // This "Return" is the secret—it prevents login from continuing
        }
        User loggedInUser = manager.validateUser(user, pass);
        if (loggedInUser != null) {
            loggedInUser.setContactInfo(contact);
            try {
                String fxmlFile;
                String title;
                double width;
                double height;

                if (loggedInUser.getRole().equals("ADMIN")) {
                    fxmlFile = "admin-dashboard.fxml";
                    title = "Admin Dashboard";
                    width = 950;
                    height = 650;
                } else {
                    fxmlFile = "customer-view.fxml";
                    title = "Customer Dashboard";
                    width = 750; // Increased width for better fit
                    height = 850; // Increased height for date pickers
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Scene scene = new Scene(loader.load(), width, height);

                if (loggedInUser.getRole().equals("CUSTOMER")) {
                    CustomerDashboardController controller = loader.getController();
                    controller.setUser(loggedInUser);
                }

                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle(title);
                stage.centerOnScreen();
                stage.show();

            } catch (Exception e) {
                errorLabel.setText("Error loading screen!");
                e.printStackTrace();
            }
        } else {

            errorLabel.setText("Invalid username or password.");
        }
    }



    @FXML
    protected void onSignupButtonClick() {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        String contactText = contactField.getText();

        if (user.isEmpty() || pass.isEmpty() || contactText.isEmpty()) {
            showErrorMessage("Registration Error", "Please fill in all fields.");
            return;
        }

        if (!contactText.matches("\\d+")) {
            showErrorMessage("Input Error", "Contact info must contain only numbers.");
            return;
        }

        boolean success = manager.registerUser(user, pass, contactText);
        if (success) {
            showInfoMessage("Account Created", "Registration successful! You can now Sign In.");
            usernameField.clear();
            passwordField.clear();
            contactField.clear();
        } else {
            showErrorMessage("Registration Error", "Username already taken.");
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