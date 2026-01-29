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

    @FXML private TextField newPlateField;
    @FXML private TextField newModelField;
    @FXML private TextField newPriceField;

    // IMPORTANT: Get the manager that already has the loaded data
    private RentalManager manager = LoginController.getManager();

    @FXML
    public void initialize() {
        plateColumn.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("dailyPrice"));
        refreshTable();
    }

    private void refreshTable() {
        vehicleTable.setItems(FXCollections.observableArrayList(manager.getAllVehicles()));
    }

    @FXML
    protected void onAddButtonClick() {
        try {
            String plate = newPlateField.getText();
            String model = newModelField.getText();
            double price = Double.parseDouble(newPriceField.getText());

            Vehicle newVehicle = VehicleFactory.createVehicle("CAR", plate, model, price);
            manager.addVehicle(newVehicle);
            refreshTable();
            newPlateField.clear(); newModelField.clear(); newPriceField.clear();
        } catch (Exception e) {
            System.out.println("Input error!");
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
            javafx.stage.Stage stage = (javafx.stage.Stage) vehicleTable.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load(), 400, 400));
            stage.setTitle("Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}