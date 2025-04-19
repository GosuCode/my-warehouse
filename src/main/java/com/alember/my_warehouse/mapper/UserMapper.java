package com.alember.my_warehouse.mapper;

import com.alember.my_warehouse.dto.user.UserRequest;
import com.alember.my_warehouse.dto.user.UserResponse;
import com.alember.my_warehouse.model.UserModel;
import org.springframework.stereotype.Component;

/**
 * Mapper class to convert between UserRequest/UserResponse DTOs and UserModel entity.
 */
@Component
public class UserMapper {

    /**
     * Converts a {@link UserRequest} DTO to a {@link UserModel} entity.
     *
     * @param request the user request DTO
     * @return the corresponding UserModel entity
     */
    public UserModel toModel(UserRequest request) {
        UserModel model = new UserModel();
        model.setUsername(request.getUsername());
        model.setEmail(request.getEmail());
        model.setPassword(request.getPassword());
        model.setRole(request.getRole());
        return model;
    }

    /**
     * Converts a {@link UserModel} entity to a {@link UserResponse} DTO.
     *
     * @param model the user model entity
     * @return the corresponding UserResponse DTO
     */
    public UserResponse toResponse(UserModel model) {
        UserResponse response = new UserResponse();
        response.setId(model.getId());
        response.setUsername(model.getUsername());
        response.setEmail(model.getEmail());
        response.setRole(model.getRole());
        return response;
    }
}
