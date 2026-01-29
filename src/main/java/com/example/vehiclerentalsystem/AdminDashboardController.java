package com.example.vehiclerentalsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class AdminDashboardController {

    // Table and Columns
    @FXML private TableView<Vehicle> vehicleTable;
    @FXML private TableColumn<Vehicle, String> plateColumn;
    @FXML private TableColumn<Vehicle, String> modelColumn;
    @FXML private TableColumn<Vehicle, Double> priceColumn;
    @FXML private TableColumn<Vehicle, Boolean> statusColumn; // NEW 4th Column

    // Input Fields
    @FXML private TextField newPlateField;
    @FXML private TextField newModelField;
    @FXML private TextField newPriceField;

    // Use the Singleton Manager to keep data synced
    private RentalManager manager = LoginController.getManager();

    @FXML
    public void initialize() {
        // Link Table Columns to the Vehicle variables
        plateColumn.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("dailyPrice"));

        // This links the 4th column to the isRented property
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("rented"));

        refreshTable();
    }

    // This updates the table view with the latest data
    private void refreshTable() {
        ObservableList<Vehicle> data = FXCollections.observableArrayList(manager.getAllVehicles());
        vehicleTable.setItems(data);
    }

    @FXML
    protected void onAddButtonClick() {
        try {
            String plate = newPlateField.getText();
            String model = newModelField.getText();
            double price = Double.parseDouble(newPriceField.getText());

            // FACTORY PATTERN: Using the factory to create the car
            Vehicle newVehicle = VehicleFactory.createVehicle("CAR", plate, model, price);

            if (newVehicle != null) {
                manager.addVehicle(newVehicle);
                refreshTable();

                // Clear the boxes
                newPlateField.clear();
                newModelField.clear();
                newPriceField.clear();
            }
        } catch (Exception e) {
            // Error handling for non-number prices
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid plate, model, and price number!");
            alert.showAndWait();
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            manager.deleteVehicle(selected);
            refreshTable();
        }
    }

    @FXML
    protected void onLogoutButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Stage stage = (Stage) vehicleTable.getScene().getWindow();

            // Set size to 400x650 to fit your new modern login design
            stage.setScene(new Scene(loader.load(), 400, 600));
            stage.setTitle("Vehicle Rental - Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}