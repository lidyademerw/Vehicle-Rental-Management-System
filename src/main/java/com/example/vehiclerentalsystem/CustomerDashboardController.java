package com.example.vehiclerentalsystem;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class CustomerDashboardController {
    @FXML private TableView<Vehicle> carTable;
    @FXML private TableColumn<Vehicle, String> carModelCol;
    @FXML private TableColumn<Vehicle, Double> carPriceCol;

    @FXML private TableView<Vehicle> motorcycleTable;
    @FXML private TableColumn<Vehicle, String> motoModelCol;
    @FXML private TableColumn<Vehicle, Double> motoPriceCol;

    @FXML private Label statusLabel;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;

    private RentalManager manager = LoginController.getManager();
    private User currentUser;

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
        Vehicle selected = carTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            selected = motorcycleTable.getSelectionModel().getSelectedItem();
        }

        if (selected == null) {
            statusLabel.setText("Error: Please select a vehicle first.");
            return;
        }

        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        if (start == null || end == null) {
            statusLabel.setText("Error: Please select both start and end dates.");
            return;
        }

        if (!end.isAfter(start)) {
            statusLabel.setText("Error: End date must be after start date.");
            return;
        }

        long days = ChronoUnit.DAYS.between(start, end);
        double totalCost = selected.calculateTotalCost((int) days);

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Rental");
        confirmAlert.setHeaderText("Rental Summary: " + selected.getModel());
        confirmAlert.setContentText("Duration: " + days + " days\nTotal Cost: $" + String.format("%.2f", totalCost));

        Optional<ButtonType> action = confirmAlert.showAndWait();

        if (action.isPresent() && action.get() == ButtonType.OK) {
            // Ensure currentUser isn't null before calling methods on it
            if (currentUser == null) {
                statusLabel.setText("Error: User session expired. Please log in again.");
                return;
            }

            String customerName = currentUser.getUsername();
            String contact = "N/A";
            if (currentUser instanceof Customer) {
                contact = ((Customer) currentUser).getContactInfo();
            }

            // Call manager with all 5 arguments
            boolean success = manager.rentVehicle(selected.getPlateNumber(), customerName, contact, start.toString(), end.toString());

            if (success) {
                statusLabel.setText("Success! Rented until " + end);
                // 3. THIS IS THE LINE THAT REMOVES THE CAR
                // refreshTables calls getAvailableCars(), which filters out isRented=true
                refreshTables();
            } else {
                statusLabel.setText("Error: Vehicle is no longer available.");
            }
        }
    }

    @FXML
    protected void onLogoutButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Stage stage = (Stage) carTable.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 400, 650)); // Adjusted for your new login height
            stage.setTitle("Login");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void setUser(User user) {
        this.currentUser = user;
    }
}