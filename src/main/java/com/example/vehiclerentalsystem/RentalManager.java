package com.example.vehiclerentalsystem;

import java.util.ArrayList;
import java.util.List;

public class RentalManager {
    private List<Vehicle> vehicles;
    private List<User> users;
    private final IVehicleRepository vehicleRepo;
    private final IUserRepository userRepo;

    public RentalManager(IVehicleRepository vehicleRepo, IUserRepository userRepo) {
        this.vehicleRepo = vehicleRepo;
        this.userRepo = userRepo;
        this.vehicles = vehicleRepo.loadVehicles();
        if (this.vehicles.isEmpty()) {
            addDefaultData();
        }

        // Load Users from file
        this.users = userRepo.loadUsers();

        // If no users exist, setup defaults and save them
        if (this.users.isEmpty()) {
            users.add(new Admin("admin", "123"));
            users.add(new Customer("user", "123","0987654321"));
            userRepo.saveUsers(users); // Save the defaults to create the file
        }
    }

    private void addDefaultData() {
        vehicles.add(new Car("ABC-111", "Toyota Corolla", 50.0));
        vehicles.add(new Car("XYZ-222", "Honda Civic", 60.0));
        vehicles.add(new Car("LUX-333", "Mercedes C-Class", 120.0));
        vehicles.add(new Motorcycle("BIKE-001", "Yamaha R1", 45.0));
        vehicles.add(new Motorcycle("BIKE-002", "Harley Davidson", 85.0));
        vehicles.add(new Motorcycle("BIKE-003", "Ducati Panigale", 110.0));
        vehicleRepo.saveVehicles(vehicles);
    }

    public boolean registerUser(String username, String password,String contactInfo) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        users.add(new Customer(username, password, contactInfo));

        // This ensures the new signup isn't lost on restart
        userRepo.saveUsers(users);
        return true;
    }

    public User validateUser(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public List<Vehicle> getAvailableCars() {
        List<Vehicle> cars = new ArrayList<>();
        for (Vehicle v : getAvailableVehicles()) {
            if (v instanceof Car) cars.add(v);
        }
        return cars;
    }

    public List<Vehicle> getAvailableMotorcycles() {
        List<Vehicle> motorcycles = new ArrayList<>();
        for (Vehicle v : getAvailableVehicles()) {
            if (v instanceof Motorcycle) motorcycles.add(v);
        }
        return motorcycles;
    }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
        vehicleRepo.saveVehicles(vehicles);
    }

    public void deleteVehicle(Vehicle v) {
        vehicles.remove(v);
        vehicleRepo.saveVehicles(vehicles);
    }

    public boolean rentVehicle(String plateNumber, String customerName, String contact, String start, String end) {
        for (Vehicle v : vehicles) {
            if (v.getPlateNumber().equals(plateNumber) && !v.isRented()) {
                v.setRentalDetails(customerName, contact, start, end);
                vehicleRepo.saveVehicles(vehicles);
                return true;
            }
        }
        return false;
    }
    public boolean returnVehicle(String plateNumber) {
        for (Vehicle v : vehicles) {
            if (v.getPlateNumber().equals(plateNumber) && v.isRented()) {
                v.setRented(false); // This triggers the reset of renter info to "N/A"
                vehicleRepo.saveVehicles(vehicles);
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