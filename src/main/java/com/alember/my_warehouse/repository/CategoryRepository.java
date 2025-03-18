package com.alember.my_warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alember.my_warehouse.model.CategoryModel;

public interface CategoryRepository extends JpaRepository<CategoryModel, String>{
	
	

}
