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

/**
 * CategoryImpl implements CategoryServices to handle business logic for categories.
 *
 * Methods:
 * - addCategory: Adds a new category after validation.
 * - getAllCategory: Retrieves all categories.
 * - updateCategory: Updates an existing category by ID.
 * - deleteCategory: Deletes a category by ID.
 * - categoryById: Retrieves a category by ID.
 */
@Service
public class CategoryImpl implements CategoryServices {

    @Autowired
    CategoryRepository categoryRepo;

    /**
     * Adds a new category after validating the request.
     * @param category The category to be added.
     * @return The saved category.
     * @throws CategoryException If the category is invalid.
     */
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

    /**
     * Retrieves all categories.
     * @return List of all categories.
     */
    @Override
    public List<CategoryModel> getAllCategory() {
        return categoryRepo.findAll();
    }

    /**
     * Updates an existing category by its ID.
     * @param id The category ID.
     * @param category The category with updated data.
     * @return The updated category.
     * @throws CategoryException If the category does not exist.
     */
    @Override
    public CategoryModel updateCategory(String id, CategoryModel category) throws CategoryException {
        if(categoryRepo.existsById(id)){
            category.setId(id);
            return categoryRepo.save(category);
        }
        throw new CategoryException("Unable to update category");
    }

    /**
     * Deletes a category by its ID.
     * @param id The category ID.
     * @throws CategoryException If the category does not exist.
     */
    public void deleteCategory(String id) throws CategoryException {
        if(categoryRepo.existsById(id)){
            categoryRepo.deleteById(id);
        }else{
            throw new CategoryException("Category with id " + id + " does not exist");
        }
    }

    /**
     * Retrieves a category by its ID.
     * @param id The category ID.
     * @return The category if found.
     * @throws CategoryException If the category does not exist.
     */
    public Optional<CategoryModel> categoryById(String id) throws CategoryException {
        Optional<CategoryModel> cat = categoryRepo.findById(id);
        if (cat.isEmpty()) {
            throw new CategoryException("Category with id " + id + " does not exist");
        }
        return cat;
    }

}
