package com.example.vehiclerentalsystem;
import java.util.List;

public interface IUserRepository {
    void saveUsers(List<User> users);
    List<User> loadUsers();
}