package com.alember.my_warehouse.services;

import com.alember.my_warehouse.model.CategoryModel;

public interface CategoryServices {
	
	public CategoryModel addCategory(CategoryModel category) throws Exception;
	public String deleteCategory(String id);

}
