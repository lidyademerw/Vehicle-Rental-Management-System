package com.example.vehiclerentalsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDataHandler {
    private static final String FILE_NAME = "vehicles.txt";

    // This method SAVES the vehicles to a text file
    public void saveVehicles(List<Vehicle> vehicles) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Vehicle v : vehicles) {
                // Determine the type using instanceof
                String type = (v instanceof Car) ? "CAR" : "MOTORCYCLE";

                // NEW Save format: Type,Plate,Model,Price,IsRented
                writer.println(type + "," + v.getPlateNumber() + "," + v.getModel() + "," + v.getDailyPrice() + "," + v.isRented());
            }
            System.out.println("Data saved successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // This method LOADS the vehicles from the text file
    public List<Vehicle> loadVehicles() {
        List<Vehicle> loadedVehicles = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return loadedVehicles;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Now we read the type from the FIRST column (parts[0])
                String type = parts[0];
                String plate = parts[1];
                String model = parts[2];
                double price = Double.parseDouble(parts[3]);
                boolean isRented = Boolean.parseBoolean(parts[4]);

                // USE THE FACTORY with the actual type from the file
                Vehicle v = VehicleFactory.createVehicle(type, plate, model, price);

                if (v != null) {
                    v.setRented(isRented);
                    loadedVehicles.add(v);
                }
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return loadedVehicles;
    }
}