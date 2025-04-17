package com.alember.my_warehouse.services.security;

import com.alember.my_warehouse.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override public boolean isEnabled() { return true; }

}
