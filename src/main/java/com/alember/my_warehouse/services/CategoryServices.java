package com.alember.my_warehouse.services;

import com.alember.my_warehouse.dto.category.CategoryRequest;
import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.model.CategoryModel;

import java.util.List;
import java.util.Optional;

public interface CategoryServices {
	
	CategoryModel addCategory(CategoryModel category) throws Exception;

	List<CategoryModel> getAllCategory();

	CategoryModel updateCategory(String id, CategoryModel category) throws CategoryException;

	Optional<CategoryModel> categoryById(String id) throws CategoryException;

	void deleteCategory(String id) throws CategoryException;
}
