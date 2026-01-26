package com.example.vehiclerentalsystem;

// Inheritance: Admin gets everything from User
public class Admin extends User {

    public Admin(String username, String password) {
        // "ADMIN" is passed automatically to the parent constructor
        super(username, password, "ADMIN");
    }

    // Later we will add methods here to "Add Car" or "Delete Car"
}
