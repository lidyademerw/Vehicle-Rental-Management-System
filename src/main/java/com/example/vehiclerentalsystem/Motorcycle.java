package com.example.vehiclerentalsystem;

// Inheritance: Motorcycle gets everything from Vehicle
public class Motorcycle extends Vehicle {

    public Motorcycle(String plateNumber, String model, double dailyPrice) {
        super(plateNumber, model, dailyPrice);
    }

    @Override
    public double calculateTotalCost(int days) {
        // Example: Motorcycles might be 10% cheaper for long rentals,
        // but for now, we'll keep it simple:
        return getDailyPrice() * days;
    }
}