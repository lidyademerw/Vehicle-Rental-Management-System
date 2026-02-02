package com.example.vehiclerentalsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//Handles user login and signup actions
public class LoginController {
    // Input fields and labels from the login screen
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private TextField contactField;
    
    // Manages users and rentals for the application
    private static RentalManager manager = new RentalManager(new FileDataHandler(), new UserDataHandler());

    public static RentalManager getManager() {
        return manager;
    }
    @FXML
    protected void onLoginButtonClick() {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        String contact = contactField.getText();

        // 1. Basic check for EVERYONE: Username and Password cannot be empty
        if (user.isEmpty() || pass.isEmpty()) {
            errorLabel.setText("Error: Username and Password are required!");
            return;
        }

        // 2. Validate the user credentials first
        User loggedInUser = manager.validateUser(user, pass);

        if (loggedInUser != null) {

            // 3. THE FIX: Check contact info ONLY IF the user is a CUSTOMER
            if (loggedInUser.getRole().equals("CUSTOMER")) {
                if (contact.isEmpty()) {
                    errorLabel.setText("Error: Contact number is required for Customers!");
                    return; // Stop here if a customer didn't type their number
                }
                // If they did type it, save it to their profile
                loggedInUser.setContactInfo(contact);
            }

            // 4. If we reach here, it means:
            // - It's an Admin (who doesn't need a contact number)
            // - OR it's a Customer who successfully provided a number.
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
                    width = 700;
                    height = 720;
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
