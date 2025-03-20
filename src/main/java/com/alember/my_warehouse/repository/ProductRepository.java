package com.alember.my_warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alember.my_warehouse.model.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, String> {


}
