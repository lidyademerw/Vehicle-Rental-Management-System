package com.example.vehiclerentalsystem;

import java.util.ArrayList;
import java.util.List;

public class RentalManager {
    private List<Vehicle> vehicles;
    private List<User> users;
    private FileDataHandler fileHandler = new FileDataHandler();

    public RentalManager() {
        this.users = new ArrayList<>();
        // Try to load from the text file first
        this.vehicles = fileHandler.loadVehicles();

        // If file is empty, add the 3 default cars
        if (this.vehicles.isEmpty()) {
            addDefaultData();
        }

        // Setup users
        users.add(new Admin("admin", "123"));
        users.add(new Customer("user", "123"));
    }

    private void addDefaultData() {
        vehicles.add(new Car("ABC-111", "Toyota Corolla", 50.0));
        vehicles.add(new Car("XYZ-222", "Honda Civic", 60.0));
        vehicles.add(new Car("LUX-333", "Mercedes C-Class", 120.0));
        fileHandler.saveVehicles(vehicles); // Save these to the file
    }

    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
        fileHandler.saveVehicles(vehicles); // SAVE TO FILE
    }

    public void deleteVehicle(Vehicle v) {
        vehicles.remove(v);
        fileHandler.saveVehicles(vehicles); // SAVE TO FILE
    }

    public boolean rentVehicle(String plateNumber) {
        for (Vehicle v : vehicles) {
            if (v.getPlateNumber().equals(plateNumber) && !v.isRented()) {
                v.setRented(true);
                fileHandler.saveVehicles(vehicles); // SAVE TO FILE
                return true;
            }
        }
        return false;
    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> available = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (!v.isRented()) available.add(v);
        }
        return available;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicles;
    }
}
