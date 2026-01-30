package com.example.vehiclerentalsystem;

import javafx.collections.FXCollections;
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
    @FXML private TableColumn<Vehicle, String> renterNameColumn;
    @FXML private TableColumn<Vehicle, String> contactColumn;
    @FXML private TableColumn<Vehicle, String> startColumn;
    @FXML private TableColumn<Vehicle, String> endColumn;

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
        renterNameColumn.setCellValueFactory(new PropertyValueFactory<>("renterName"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("renterContact"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        refreshTable();
    }

    private void refreshTable() {
        vehicleTable.setItems(FXCollections.observableArrayList(manager.getAllVehicles()));
    }

    @FXML
    protected void onAddButtonClick() {
        handleVehicleAddition("CAR");
    }

    @FXML
    protected void onAddMotorcycleButtonClick() {
        handleVehicleAddition("MOTORCYCLE");
    }

    private void handleVehicleAddition(String type) {
        try {
            String plate = newPlateField.getText();
            String model = newModelField.getText();
            double price = Double.parseDouble(newPriceField.getText());

            Vehicle newVehicle = VehicleFactory.createVehicle(type, plate, model, price);

            if (newVehicle != null) {
                manager.addVehicle(newVehicle);
                refreshTable();
                clearFields();
            }
        } catch (Exception e) {
            showError("Please enter valid vehicle details!");
        }
    }

    // process a return
    @FXML
    protected void onReturnButtonClick() {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (selected.isRented()) {
                manager.returnVehicle(selected.getPlateNumber());
                vehicleTable.refresh();
                refreshTable();
            } else {
                showError("This vehicle is already available!");
            }
        } else {
            showError("Please select a rented vehicle to return.");
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

    private void clearFields() {
        newPlateField.clear();
        newModelField.clear();
        newPriceField.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
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