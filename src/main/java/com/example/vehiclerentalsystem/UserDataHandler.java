package com.example.vehiclerentalsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDataHandler {
    private static final String FILE_NAME = "users.txt";

    public void saveUsers(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (User u : users) {
                String role = (u instanceof Admin) ? "ADMIN" : "CUSTOMER";
                writer.println(role + "," + u.getUsername() + "," + u.getPassword());
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public List<User> loadUsers() {
        List<User> loadedUsers = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return loadedUsers;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String role = parts[0];
                String username = parts[1];
                String password = parts[2];

                if (role.equals("ADMIN")) {
                    loadedUsers.add(new Admin(username, password));
                } else {
                    loadedUsers.add(new Customer(username, password));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return loadedUsers;
    }
}