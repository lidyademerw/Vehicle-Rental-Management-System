package com.example.vehiclerentalsystem;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.util.Optional;

public class CustomerDashboardController {
    @FXML private TableView<Vehicle> carTable;
    @FXML private TableColumn<Vehicle, String> carModelCol;
    @FXML private TableColumn<Vehicle, Double> carPriceCol;

    @FXML private TableView<Vehicle> motorcycleTable;
    @FXML private TableColumn<Vehicle, String> motoModelCol;
    @FXML private TableColumn<Vehicle, Double> motoPriceCol;

    @FXML private Label statusLabel;

    private RentalManager manager = LoginController.getManager();

    @FXML
    public void initialize() {
        carModelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        carPriceCol.setCellValueFactory(new PropertyValueFactory<>("dailyPrice"));

        motoModelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        motoPriceCol.setCellValueFactory(new PropertyValueFactory<>("dailyPrice"));

        refreshTables();
    }

    private void refreshTables() {
        carTable.setItems(FXCollections.observableArrayList(manager.getAvailableCars()));
        motorcycleTable.setItems(FXCollections.observableArrayList(manager.getAvailableMotorcycles()));
    }

    @FXML
    protected void onRentButtonClick() {
        // 1. Identify which vehicle is selected
        Vehicle selected = carTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            selected = motorcycleTable.getSelectionModel().getSelectedItem();
        }

        if (selected != null) {
            // 2. Ask user for rental duration
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Rental Duration");
            dialog.setHeaderText("Renting: " + selected.getModel());
            dialog.setContentText("Please enter the number of days:");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                try {
                    int days = Integer.parseInt(result.get());

                    if (days <= 0) {
                        statusLabel.setText("Error: Days must be at least 1.");
                        return;
                    }

                    // 3. Calculate total cost using the method in your Vehicle class
                    double totalCost = selected.calculateTotalCost(days);

                    // 4. Show confirmation alert with the calculated cost
                    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmAlert.setTitle("Confirm Rental");
                    confirmAlert.setHeaderText("Total Cost: $" + String.format("%.2f", totalCost));
                    confirmAlert.setContentText("Are you sure you want to rent this " +
                            selected.getClass().getSimpleName() + " for " + days + " days?");

                    Optional<ButtonType> action = confirmAlert.showAndWait();

                    if (action.isPresent() && action.get() == ButtonType.OK) {
                        manager.rentVehicle(selected.getPlateNumber());
                        statusLabel.setText("Success! Paid $" + String.format("%.2f", totalCost) + " for " + selected.getModel());
                        refreshTables();
                    }
                } catch (NumberFormatException e) {
                    statusLabel.setText("Error: Please enter a valid whole number for days.");
                }
            }
        } else {
            statusLabel.setText("Error: Please select a vehicle from one of the tables first.");
        }
    }

    @FXML
    protected void onLogoutButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Stage stage = (Stage) carTable.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 400, 600));
        } catch (Exception e) { e.printStackTrace(); }
    }
}