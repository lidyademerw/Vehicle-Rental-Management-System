package com.example.vehiclerentalsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    // We make this static so the whole app shares the same list of cars
    private static RentalManager manager = new RentalManager();

    public static RentalManager getManager() {
        return manager;
    }

    @FXML
    protected void onLoginButtonClick() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        User loggedInUser = manager.login(user, pass);

        if (loggedInUser != null) {
            try {
                // If it's an Admin, open the Admin Dashboard
                if (loggedInUser.getRole().equals("ADMIN")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin-dashboard.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 500);
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Admin Dashboard");
                }
                // We will add the Customer dashboard logic tomorrow!
                else {
                    errorLabel.setText("Customer login works, but dashboard coming tomorrow!");
                }
            } catch (Exception e) {
                errorLabel.setText("Error loading the next screen!");
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Invalid username or password!");
        }
    }
}