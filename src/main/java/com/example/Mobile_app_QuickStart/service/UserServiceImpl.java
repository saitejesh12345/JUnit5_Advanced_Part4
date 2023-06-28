package com.example.Mobile_app_QuickStart.service;

import com.example.Mobile_app_QuickStart.dao.UserRepository;

import com.example.Mobile_app_QuickStart.exception.ResourceNotFoundException;
import com.example.Mobile_app_QuickStart.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElse(null);
    }



    @Override
    public List<User> addUsers(List<User> user) {

    //    Optional<User> saveUser = userRepository.findByEmail()
        return userRepository.saveAll(user);
    }

    @Override
    public User addSingleUser(User user) {
        Optional<User> saveUser = userRepository.findByEmail(user.getEmail());
       if(saveUser.isPresent()){
           throw new ResourceNotFoundException("User already exist with same email_ID"+user.getEmail());
       }
       return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(int id, String email) {
         User user = userRepository.findById(id).orElse(null);
         user.setEmail(email);
         return userRepository.save(user);
    }

    @Override
    public User deleteUser(int id) {
        userRepository.deleteById(id);
        return null;
    }


}
