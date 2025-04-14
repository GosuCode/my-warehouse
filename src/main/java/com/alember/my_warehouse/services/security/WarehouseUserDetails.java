package com.alember.my_warehouse.services.security;

import com.alember.my_warehouse.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class WarehouseUserDetails implements UserDetails {

    private String username;

    private String password;

    private List<String> roles;

    public WarehouseUserDetails(String username, String password, List<String> roles){
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public WarehouseUserDetails(UserModel user){
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(roles);
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
