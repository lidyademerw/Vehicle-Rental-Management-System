package com.example.vehiclerentalsystem;

public abstract class User {
    // Private fields (Encapsulation)
    private String username;
    private String password;
    private String role;// This will be "ADMIN" or "CUSTOMER"
    private String contactInfo;

    public User(String username, String password, String role, String contactInfo) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.contactInfo = contactInfo;
    }

    // Getters so we can check login details later
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}
