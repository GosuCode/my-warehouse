package com.alember.my_warehouse.dto.user;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for sending login response data.
 * Contains the JWT token and its expiration time.
 */
@Getter
@Setter
public class LoginResponse {

    /**
     * The JWT token assigned after successful authentication.
     */
    private String token;

    /**
     * Expiration time of the token in milliseconds.
     */
    private long expiresIn;
}
