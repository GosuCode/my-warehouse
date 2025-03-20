package com.alember.my_warehouse.services;

import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.model.CategoryModel;

import java.util.List;

public interface CategoryServices {
	
	public CategoryModel addCategory(CategoryModel category) throws Exception;

	List<CategoryModel> getAllCategory();

	CategoryModel updateCategory(String id, CategoryModel category) throws CategoryException;

}
