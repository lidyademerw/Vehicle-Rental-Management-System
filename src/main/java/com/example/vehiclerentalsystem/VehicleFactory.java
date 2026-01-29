package com.example.vehiclerentalsystem;

public class VehicleFactory {
    // This is the FACTORY PATTERN: It creates objects for us
    public static Vehicle createVehicle(String type, String plate, String model, double price) {
        if (type.equalsIgnoreCase("CAR")) {
            return new Car(plate, model, price);
        }
        // You can add more types like "MOTORCYCLE" here later for bonus marks
        return null;
    }
}