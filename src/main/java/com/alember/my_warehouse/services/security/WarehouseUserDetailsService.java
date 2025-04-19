package com.alember.my_warehouse.services.security;

import com.alember.my_warehouse.model.UserModel;
import com.alember.my_warehouse.repository.UserRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of {@link UserDetailsService} for Spring Security.
 * Loads user-specific data for authentication.
 */
@Service
public class WarehouseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads a user by username and converts it to {@link UserDetails} for Spring Security.
     *
     * @param username the username identifying the user
     * @return {@link UserDetails} containing user credentials and authorities
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username);

        if (user == null || ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("User does not exist");
        }

        return new WarehouseUserDetails(user.getUsername(), user.getPassword(), user.getRole());
    }
}
