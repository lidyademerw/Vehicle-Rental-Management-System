package com.example.vehiclerentalsystem;

public class Customer extends User {

    public Customer(String username, String password) {
        super(username, password, "CUSTOMER");
    }

    // Customers will eventually have a list of cars they rented
}
