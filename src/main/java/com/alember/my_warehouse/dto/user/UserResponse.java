package com.alember.my_warehouse.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private List<String> role;
}
