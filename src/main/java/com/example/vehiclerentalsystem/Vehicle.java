package com.example.vehiclerentalsystem;


public abstract class Vehicle {
    // Encapsulated data fields to protect object state
    private String plateNumber;
    private String model;
    private double dailyPrice;
    private boolean isRented;
    private String renterName;
    private String renterContact;
    private String startDate;
    private String endDate;
    // Constructor to initialize a Vehicle object with specific details
    public Vehicle(String plateNumber, String model, double dailyPrice) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.dailyPrice = dailyPrice;
        this.isRented = false;
        this.renterName = "N/A";
        this.renterContact = "N/A";
        this.startDate = "N/A";
        this.endDate = "N/A";
    }
    // Getter method to provide read-only access to the plate number
    public String getPlateNumber() { return plateNumber; }
    public String getModel() { return model; }
    public double getDailyPrice() { return dailyPrice; }
    public boolean isRented() { return isRented; }
    public String getRenterName() { return renterName; }
    public String getRenterContact() { return renterContact; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }

    // Updated to clear info when car is returned
    public void setRented(boolean rented) {
        this.isRented = rented;
        if (!rented) {
            this.renterName = "N/A";
            this.renterContact = "N/A";
            this.startDate = "N/A";
            this.endDate = "N/A";
        }
    }

    // To be called by RentalManager when a booking happens
    public void setRentalDetails(String name, String contact, String start, String end) {
        this.renterName = name;
        this.renterContact = contact;
        this.startDate = start;
        this.endDate = end;
        this.isRented = true;
    }

    public abstract double calculateTotalCost(int days);
}