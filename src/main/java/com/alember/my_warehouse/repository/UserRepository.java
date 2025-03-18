package com.alember.my_warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alember.my_warehouse.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, String>{

}
