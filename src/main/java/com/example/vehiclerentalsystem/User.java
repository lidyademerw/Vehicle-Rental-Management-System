package com.example.vehiclerentalsystem;

public abstract class User {
    // Private fields (Encapsulation)
    private String username;
    private String password;
    private String role; // This will be "ADMIN" or "CUSTOMER"

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters so we can check login details later
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}
