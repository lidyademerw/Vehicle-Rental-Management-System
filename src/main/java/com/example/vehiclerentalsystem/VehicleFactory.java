package com.example.vehiclerentalsystem;

public class VehicleFactory {
    public static Vehicle createVehicle(String type, String plate, String model, double price) {
        if (type.equalsIgnoreCase("CAR")) {
            return new Car(plate, model, price);
        }
        // ADD MOTORCYCLE
        else if (type.equalsIgnoreCase("MOTORCYCLE")) {
            return new Motorcycle(plate, model, price);
        }
        return null;
    }
}