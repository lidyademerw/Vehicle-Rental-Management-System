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

    @FXML private TableView<Vehicle> vehicleTable;
    @FXML private TableColumn<Vehicle, String> plateColumn;
    @FXML private TableColumn<Vehicle, String> modelColumn;
    @FXML private TableColumn<Vehicle, Double> priceColumn;
    @FXML private TableColumn<Vehicle, Boolean> statusColumn;

    @FXML private TextField newPlateField;
    @FXML private TextField newModelField;
    @FXML private TextField newPriceField;

    private RentalManager manager = LoginController.getManager();

    @FXML
    public void initialize() {
        plateColumn.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("dailyPrice"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("rented"));

        refreshTable();
    }

    private void refreshTable() {
        vehicleTable.setItems(FXCollections.observableArrayList(manager.getAllVehicles()));
    }

    // Existing button updated to specifically call for a CAR
    @FXML
    protected void onAddButtonClick() {
        handleVehicleAddition("CAR");
    }

    // NEW Method for the Motorcycle button
    @FXML
    protected void onAddMotorcycleButtonClick() {
        handleVehicleAddition("MOTORCYCLE");
    }

    // Helper method to reduce code duplication
    private void handleVehicleAddition(String type) {
        try {
            String plate = newPlateField.getText();
            String model = newModelField.getText();
            double price = Double.parseDouble(newPriceField.getText());

            // FACTORY PATTERN: Now dynamically choosing CAR or MOTORCYCLE
            Vehicle newVehicle = VehicleFactory.createVehicle(type, plate, model, price);

            if (newVehicle != null) {
                manager.addVehicle(newVehicle);
                refreshTable();

                newPlateField.clear();
                newModelField.clear();
                newPriceField.clear();
            }
        } catch (Exception e) {
            showError("Please enter a valid plate, model, and price number!");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
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
            stage.setScene(new Scene(loader.load(), 400, 600));
        } catch (Exception e) { e.printStackTrace(); }
    }
}