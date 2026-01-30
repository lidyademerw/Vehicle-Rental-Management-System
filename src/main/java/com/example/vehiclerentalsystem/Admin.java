package com.example.vehiclerentalsystem;

// Inheritance: Admin gets everything from User
public class Admin extends User {

    public Admin(String username, String password) {
        // "ADMIN" is passed automatically to the parent constructor
        super(username, password, "ADMIN", "0912345678");
    }
    public void addCar (Car car) {
    // Logic to add car to system
    }
    public void deleteCar(String carId) {
        // Logic to delete car from system
    }

    // Later we will add methods to "Add Car" or "Delete Car"
}
