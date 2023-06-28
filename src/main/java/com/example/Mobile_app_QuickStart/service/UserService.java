package com.example.Mobile_app_QuickStart.service;

import com.example.Mobile_app_QuickStart.model.User;

import java.util.List;

public interface UserService {
    User getUserById(int id);

    List<User> addUsers(List<User> user);
User addSingleUser(User user);
    List<User> getAllUsers();

    User updateUser(User user);

    User updateUser(int id, String email);

    User deleteUser(int id);


}
