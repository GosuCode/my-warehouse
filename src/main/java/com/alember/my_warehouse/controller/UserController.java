package com.alember.my_warehouse.controller;

import com.alember.my_warehouse.dto.ApiResponse;
import com.alember.my_warehouse.dto.user.LoginResponse;
import com.alember.my_warehouse.dto.user.UserRequest;
import com.alember.my_warehouse.dto.user.UserResponse;
import com.alember.my_warehouse.enums.ApiStatus;
import com.alember.my_warehouse.exception.UserNotFoundException;
import com.alember.my_warehouse.mapper.UserMapper;
import com.alember.my_warehouse.model.UserModel;
import com.alember.my_warehouse.services.impl.UserImpl;
import com.alember.my_warehouse.services.security.JwtService;
import com.alember.my_warehouse.services.security.WarehouseUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for user-related operations such as registration, authentication,
 * retrieval, and deletion of users.
 */
@RestController
@RequestMapping("/api")
@SuppressWarnings("unused")
public class UserController {

    @Autowired
    UserImpl userServices;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    WarehouseUserDetailsService warehouseUserDetailsService;

    /**
     * Registers a new user and returns a JWT token upon successful registration.
     *
     * @param userRequest the user request payload
     * @return LoginResponse containing JWT token
     */
    @PostMapping("/user/register")
    public LoginResponse register(@RequestBody @Valid UserRequest userRequest) {
        ApiResponse apiResponse = new ApiResponse();
        UserModel user = userMapper.toModel(userRequest);

        UserModel createdUser = userServices.signUp(user);
        UserResponse response = userMapper.toResponse(createdUser);

        apiResponse.setStatusCode(200);
        apiResponse.setStatus(ApiStatus.SUCCESS);
        apiResponse.setDescription("User created successfully.");
        apiResponse.setData(response);

        userServices.authenticate(userRequest.getUsername(), userRequest.getPassword());
        UserDetails userDetails = warehouseUserDetailsService.loadUserByUsername(userRequest.getUsername());

        String jwtToken = jwtService.generateToken(userDetails);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        return loginResponse;
    }

    /**
     * Authenticates a user and returns a JWT token if credentials are valid.
     *
     * @param userRequest the login request payload
     * @return LoginResponse containing JWT token
     */
    @PostMapping("/login")
    public LoginResponse authenticate(@RequestBody UserRequest userRequest) {
        userServices.authenticate(userRequest.getUsername(), userRequest.getPassword());
        UserDetails userDetails = warehouseUserDetailsService.loadUserByUsername(userRequest.getUsername());

        String jwtToken = jwtService.generateToken(userDetails);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        return loginResponse;
    }

    /**
     * Retrieves all users in the system.
     *
     * @return ApiResponse containing list of all users
     */
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

    /**
     * Retrieves a user by their ID.
     *
     * @param id the user's ID
     * @return ApiResponse containing user details or error if not found
     */
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

    /**
     * Deletes a user by their ID.
     *
     * @param id the user's ID
     * @return ApiResponse indicating success or failure of the operation
     */
    @DeleteMapping("/user/{id}")
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
