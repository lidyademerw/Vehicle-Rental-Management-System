package com.example.vehiclerentalsystem;
import java.util.List;

public interface IVehicleRepository {
    void saveVehicles(List<Vehicle> vehicles);
    List<Vehicle> loadVehicles();
}