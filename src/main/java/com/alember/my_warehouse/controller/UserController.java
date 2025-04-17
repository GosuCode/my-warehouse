package com.alember.my_warehouse.controller;

import com.alember.my_warehouse.dto.ApiResponse;
import com.alember.my_warehouse.dto.user.UserRequest;
import com.alember.my_warehouse.dto.user.UserResponse;
import com.alember.my_warehouse.enums.ApiStatus;
import com.alember.my_warehouse.exception.UserNotFoundException;
import com.alember.my_warehouse.mapper.UserMapper;
import com.alember.my_warehouse.model.UserModel;
import com.alember.my_warehouse.services.impl.UserImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserImpl userServices;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/user/register/")
    public ApiResponse createUser(@RequestBody @Valid UserRequest userRequest) {
        ApiResponse apiResponse = new ApiResponse();
        UserModel user = userMapper.toModel(userRequest);

        UserModel createdUser = userServices.createUser(user);
        UserResponse response = userMapper.toResponse(createdUser);

        apiResponse.setStatusCode(200);
        apiResponse.setStatus(ApiStatus.SUCCESS);
        apiResponse.setDescription("User created successfully.");
        apiResponse.setData(response);
        return apiResponse;
    }

    @GetMapping("/users/")
    public ApiResponse getAllUsers() {
        ApiResponse apiResponse = new ApiResponse();
        List<UserModel> users = userServices.getAllUsers();
        List<UserResponse> responseList = users.stream().map(userMapper::toResponse).toList();

        apiResponse.setStatusCode(200);
        apiResponse.setStatus(ApiStatus.SUCCESS);
        apiResponse.setDescription("All users fetched successfully.");
        apiResponse.setData(responseList);
        return apiResponse;
    }

    @GetMapping("/user/{id}")
    public ApiResponse getUserById(@PathVariable String id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            UserModel user = userServices.getUser(id).orElseThrow(() -> new UserNotFoundException("User not found."));
            UserResponse response = userMapper.toResponse(user);

            apiResponse.setStatusCode(200);
            apiResponse.setStatus(ApiStatus.SUCCESS);
            apiResponse.setDescription("User fetched successfully.");
            apiResponse.setData(response);
        } catch (UserNotFoundException e) {
            apiResponse.setStatusCode(404);
            apiResponse.setStatus(ApiStatus.ERROR);
            apiResponse.setDescription(e.getMessage());
        }
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteUser(@PathVariable String id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            userServices.deleteUser(id);
            apiResponse.setStatusCode(200);
            apiResponse.setStatus(ApiStatus.SUCCESS);
            apiResponse.setDescription("User deleted successfully.");
        } catch (Exception e) {
            apiResponse.setStatusCode(500);
            apiResponse.setStatus(ApiStatus.ERROR);
            apiResponse.setDescription("An error occurred during deletion.");
        }
        return apiResponse;
    }

}
