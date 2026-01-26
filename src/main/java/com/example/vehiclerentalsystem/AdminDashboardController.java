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

    @FXML private TextField newPlateField;
    @FXML private TextField newModelField;
    @FXML private TextField newPriceField;

    private RentalManager manager = LoginController.getManager();

    @FXML
    public void initialize() {
        // Link Table Columns to the Vehicle variables
        plateColumn.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("dailyPrice"));

        refreshTable();
    }

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

            manager.getAllVehicles().add(new Car(plate, model, price));
            refreshTable();
            newPlateField.clear(); newModelField.clear(); newPriceField.clear();
        } catch (Exception e) {
            System.out.println("Invalid Input!");
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            manager.getAllVehicles().remove(selected);
            refreshTable();
        }
    }

    @FXML
    protected void onLogoutButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Stage stage = (Stage) vehicleTable.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 400, 400));
            stage.setTitle("Login");
        } catch (Exception e) { e.printStackTrace(); }
    }
}