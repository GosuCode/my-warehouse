package com.alember.my_warehouse.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.model.CategoryModel;
import com.alember.my_warehouse.repository.CategoryRepository;
import com.alember.my_warehouse.services.CategoryServices;
import com.alember.my_warehouse.validator.InputRequestValidator;

@Service
public class CategoryImpl implements CategoryServices {

	@Autowired
	CategoryRepository categoryRepo;

	@Override
	public String deleteCategory(String id) {
		categoryRepo.deleteById(id);
		return "successfully deleted";
	}

	@Override
	public CategoryModel addCategory(CategoryModel category) throws Exception {
		try {
			InputRequestValidator.validateCategoryRequest(category);
		} catch (CategoryException ex) {
			System.out.println("Exception occurs: " + ex);
			throw ex;
		}
		CategoryModel cat = categoryRepo.save(category);
		return cat;
	}

}
