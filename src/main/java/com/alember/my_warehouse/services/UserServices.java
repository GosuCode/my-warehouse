package com.alember.my_warehouse.services;

import com.alember.my_warehouse.exception.UserNotFoundException;
import com.alember.my_warehouse.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserServices {

    public UserModel createUser(UserModel user);

    public Optional<UserModel> getUser(String id) throws UserNotFoundException;

    public void deleteUser(String id);
    
    public List<UserModel> getAllUsers();

}
