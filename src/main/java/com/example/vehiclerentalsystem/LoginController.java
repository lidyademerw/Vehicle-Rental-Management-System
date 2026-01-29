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
                String fxmlFile = "";
                String title = "";

                // Switch based on Role
                if (loggedInUser.getRole().equals("ADMIN")) {
                    fxmlFile = "admin-dashboard.fxml";
                    title = "Admin Dashboard";
                } else {
                    fxmlFile = "customer-view.fxml";
                    title = "Customer Dashboard";
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Scene scene = new Scene(loader.load(), 600, 550);
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle(title);
                stage.show();

            } catch (Exception e) {
                errorLabel.setText("Error loading screen!");
                e.printStackTrace();
            }

        }
    }
}