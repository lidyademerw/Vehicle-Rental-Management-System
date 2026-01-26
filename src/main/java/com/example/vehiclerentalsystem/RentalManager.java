package com.example.vehiclerentalsystem;

import java.util.ArrayList;
import java.util.List;

public class RentalManager {
    // These lists hold all our data while the app is running
    private List<Vehicle> vehicles;
    private List<User> users;

    public RentalManager() {
        this.vehicles = new ArrayList<>();
        this.users = new ArrayList<>();

        // Let's add some "Default" data so the app isn't empty when we start
        addDefaultData();
    }

    // This method puts some starter cars and users in the system
    private void addDefaultData() {
        // Adding an Admin and a Customer
        users.add(new Admin("admin", "123"));
        users.add(new Customer("user", "123"));

        // Adding some Cars
        vehicles.add(new Car("ABC-111", "Toyota Corolla", 50.0));
        vehicles.add(new Car("XYZ-222", "Honda Civic", 60.0));
        vehicles.add(new Car("LUX-333", "Mercedes C-Class", 120.0));
    }

    // Logic: Find a user by username and password (Login)
    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u; // Login success!
            }
        }
        return null; // Login failed
    }

    // Logic: Get only the cars that are NOT rented yet
    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> available = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (!v.isRented()) {
                available.add(v);
            }
        }
        return available;
    }

    // Logic: Get ALL vehicles (for the Admin)
    public List<Vehicle> getAllVehicles() {
        return vehicles;
    }

    // Logic: Rent a car
    public boolean rentVehicle(String plateNumber) {
        for (Vehicle v : vehicles) {
            if (v.getPlateNumber().equals(plateNumber) && !v.isRented()) {
                v.setRented(true);
                return true; // Success
            }
        }
        return false; // Could not rent (already rented or wrong plate)
    }
}