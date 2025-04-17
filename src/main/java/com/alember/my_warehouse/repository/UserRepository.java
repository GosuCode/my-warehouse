package com.alember.my_warehouse.repository;

import com.alember.my_warehouse.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.alember.my_warehouse.model.UserModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String>{

    UserModel findByUsername(String username) throws UsernameNotFoundException;

//    UserModel existByEmail(String email) throws UserNotFoundException;

}
