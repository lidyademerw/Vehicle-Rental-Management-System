package com.example.vehiclerentalsystem;

// Car gets everything from Vehicle (Inheritance)
public class Car extends Vehicle {

    public Car(String plateNumber, String model, double dailyPrice) {
        super(plateNumber, model, dailyPrice); // Send the info to the Vehicle "parent"
    }

    @Override
    public double calculateTotalCost(int days) {
        // Math: Price per day * number of days
        return getDailyPrice() * days;
    }
}
