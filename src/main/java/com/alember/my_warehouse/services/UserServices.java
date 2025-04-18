package com.alember.my_warehouse.services;

import com.alember.my_warehouse.dto.user.UserRequest;
import com.alember.my_warehouse.exception.UserNotFoundException;
import com.alember.my_warehouse.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserServices {

    UserModel signUp(UserModel user);

    UserModel authenticate(String username, String password);

    Optional<UserModel> getUser(String id) throws UserNotFoundException;

    void deleteUser(String id);
    
    List<UserModel> getAllUsers();

}
