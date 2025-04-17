package com.alember.my_warehouse.mapper;

import com.alember.my_warehouse.dto.user.UserRequest;
import com.alember.my_warehouse.dto.user.UserResponse;
import com.alember.my_warehouse.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserModel toModel(UserRequest request) {
        UserModel model = new UserModel();
        model.setUsername(request.getUsername());
        model.setEmail(request.getEmail());
        model.setPassword(request.getPassword());
        return model;
    }

    public UserResponse toResponse(UserModel model) {
        UserResponse response = new UserResponse();
        response.setId(model.getId());
        response.setUsername(model.getUsername());
        response.setEmail(model.getEmail());
        response.setRole(model.getRole());
        return response;
    }
}
