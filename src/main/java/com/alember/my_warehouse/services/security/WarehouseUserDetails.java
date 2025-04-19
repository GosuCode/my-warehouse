package com.alember.my_warehouse.services.security;

import com.alember.my_warehouse.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Custom implementation of {@link UserDetails} that encapsulates
 * user information used by Spring Security for authentication and authorization.
 */
public class WarehouseUserDetails implements UserDetails {

    private String username;
    private String password;
    private List<String> roles;

    /**
     * Constructs a user details object with the given username, password, and roles.
     *
     * @param username the username
     * @param password the password
     * @param roles the list of roles
     */
    public WarehouseUserDetails(String username, String password, List<String> roles){
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    /**
     * Constructs a user details object from a {@link UserModel}.
     * Note: roles must be manually set later.
     *
     * @param user the user model instance
     */
    public WarehouseUserDetails(UserModel user){
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public String getUsername() { return this.username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

}
