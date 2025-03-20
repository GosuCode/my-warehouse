package com.alember.my_warehouse.services.impl;

import com.alember.my_warehouse.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.model.CategoryModel;
import com.alember.my_warehouse.services.CategoryServices;
import com.alember.my_warehouse.validator.InputRequestValidator;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryImpl implements CategoryServices {

    @Autowired
    CategoryRepository categoryRepo;

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

    @Override
    public List<CategoryModel> getAllCategory() {
        return categoryRepo.findAll();
    }

    @Override
    public CategoryModel updateCategory(String id, CategoryModel category) throws CategoryException {
        if(categoryRepo.existsById(id)){
            category.setId(id);
            return categoryRepo.save(category);
        }
        throw new CategoryException("Unable to update category");
    }

    public void deleteCategory(String id) throws CategoryException {
        if(categoryRepo.existsById(id)){
            categoryRepo.deleteById(id);
        }else{
            throw new CategoryException("Category with id " + id + " does not exist");
        }
    }

    public Optional<CategoryModel> findById(String id) throws CategoryException {
        if(categoryRepo.existsById(id)){
            Optional<CategoryModel> cat = categoryRepo.findById(id);
            return cat;
        }else{
            throw new CategoryException("Category with id " + id + " does not exist");
        }
    }

}
