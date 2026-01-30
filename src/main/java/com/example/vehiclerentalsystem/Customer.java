package com.example.vehiclerentalsystem;

public class Customer extends User {

    public Customer(String username, String password, String contactInfo) {
        super(username, password, "CUSTOMER", contactInfo);
    }

}
