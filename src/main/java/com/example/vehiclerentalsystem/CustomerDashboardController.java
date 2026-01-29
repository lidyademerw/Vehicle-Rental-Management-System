package com.example.vehiclerentalsystem;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class CustomerDashboardController {
    @FXML private TableView<Vehicle> availableTable;
    @FXML private TableColumn<Vehicle, String> modelCol;
    @FXML private TableColumn<Vehicle, Double> priceCol;
    @FXML private Label statusLabel;

    private RentalManager manager = LoginController.getManager(); // save admin and user data in same file

    @FXML
    public void initialize() {
        // Link columns to Vehicle data
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("dailyPrice"));
        refreshTable();
    }

    private void refreshTable() {
        // Only show cars where isRented is false
        availableTable.setItems(FXCollections.observableArrayList(manager.getAvailableVehicles()));
    }

    @FXML
    protected void onRentButtonClick() {
        Vehicle selected = availableTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            manager.rentVehicle(selected.getPlateNumber());
            statusLabel.setText("Success! You have rented: " + selected.getModel());
            refreshTable(); // Refresh so the car disappears from available list
        } else {
            statusLabel.setText("Error: Please select a car first.");
        }
    }

    @FXML
    protected void onLogoutButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Stage stage = (Stage) availableTable.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 400, 600));
        } catch (Exception e) { e.printStackTrace(); }
    }
}