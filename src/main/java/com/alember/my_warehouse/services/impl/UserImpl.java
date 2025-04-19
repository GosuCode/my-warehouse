package com.alember.my_warehouse.services.impl;

import com.alember.my_warehouse.dto.user.UserRequest;
import com.alember.my_warehouse.model.UserModel;
import com.alember.my_warehouse.repository.UserRepository;
import com.alember.my_warehouse.services.UserServices;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserImpl implements UserServices {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    public UserImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder encoder,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserModel signUp(UserModel user) {
//        user.setRole(List.of("ADMIN"));
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

    @Override
    public UserModel authenticate(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return userRepository.findByUsername(username);
    }
}
