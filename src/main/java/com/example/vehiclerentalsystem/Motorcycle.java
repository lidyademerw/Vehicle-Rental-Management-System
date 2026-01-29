package com.example.vehiclerentalsystem;

// Motorcycle gets everything from Vehicle
public class Motorcycle extends Vehicle {

    public Motorcycle(String plateNumber, String model, double dailyPrice) {
        super(plateNumber, model, dailyPrice);
    }

    @Override
    public double calculateTotalCost(int days) {

        return getDailyPrice() * days;
    }
}