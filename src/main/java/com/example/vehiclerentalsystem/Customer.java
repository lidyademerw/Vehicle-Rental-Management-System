package com.example.vehiclerentalsystem;

public class Customer extends User {
    public Customer(String username, String password, String contactInfo) {
        // Passes everything to the User parent class
        super(username, password, "CUSTOMER", contactInfo);
    }
}
