package com.example.vehiclerentalsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDataHandler {
    private static final String FILE_NAME = "vehicles.txt";

    // This method SAVES the cars to a text file
    public void saveVehicles(List<Vehicle> vehicles) {
        // "try-with-resources" handles closing the file automatically
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Vehicle v : vehicles) {
                // Save format: Plate,Model,Price,IsRented
                writer.println(v.getPlateNumber() + "," + v.getModel() + "," + v.getDailyPrice() + "," + v.isRented());
            }
            System.out.println("Data saved successfully to " + FILE_NAME);
        } catch (IOException e) {
            // This is "Exception Handling" - mandatory for your project!
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // This method LOADS the cars from the text file
    public List<Vehicle> loadVehicles() {
        List<Vehicle> loadedVehicles = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return loadedVehicles; // Return empty list if no file yet

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Create a new Car object from the text line
                Car car = new Car(parts[0], parts[1], Double.parseDouble(parts[2]));
                car.setRented(Boolean.parseBoolean(parts[3]));
                loadedVehicles.add(car);
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return loadedVehicles;
    }
}
