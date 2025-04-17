package com.alember.my_warehouse.services.impl;

import com.alember.my_warehouse.model.UserModel;
import com.alember.my_warehouse.repository.UserRepository;
import com.alember.my_warehouse.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserImpl implements UserServices {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public UserModel createUser(UserModel user) {
        user.setRole(List.of("USER"));
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<UserModel> getUser(String id) {
        Optional<UserModel> user = userRepository.findById(id);
        return user;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserModel> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        return users;
    }
}
