package com.example.vehiclerentalsystem;
public abstract class Vehicle {
        // These are PRIVATE so only this class can touch them (Encapsulation)
        private String plateNumber;
        private String model;
        private double dailyPrice;
        private boolean isRented;

        // The Constructor: This is how we "build" a vehicle in the code
        public Vehicle(String plateNumber, String model, double dailyPrice) {
            this.plateNumber = plateNumber;
            this.model = model;
            this.dailyPrice = dailyPrice;
            this.isRented = false; // It starts as not rented
        }

        // GETTERS: These allow other classes to "see" the private data
        public String getPlateNumber() { return plateNumber; }
        public String getModel() { return model; }
        public double getDailyPrice() { return dailyPrice; }
        public boolean isRented() { return isRented; }

        // SETTER: This allows us to change the status
        public void setRented(boolean rented) { isRented = rented; }

        // ABSTRACT METHOD: This says "All vehicles must calculate cost, but they do it differently"
        public abstract double calculateTotalCost(int days);
    }

