package com.alember.my_warehouse.services.security;

import com.alember.my_warehouse.model.UserModel;
import com.alember.my_warehouse.repository.UserRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WarehouseUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        WarehouseUserDetails warehouseUserDetails;

        UserModel user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User does not exist");
        }

        if(!ObjectUtils.isEmpty(user)){
            warehouseUserDetails = new WarehouseUserDetails(user.getUsername(), user.getPassword(), user.getRole());
        }else{
            throw new UsernameNotFoundException("User does not exist");
        }
        return warehouseUserDetails;
    }


}
