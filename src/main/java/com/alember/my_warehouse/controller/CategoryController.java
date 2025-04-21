package com.alember.my_warehouse.controller;

import com.alember.my_warehouse.dto.category.CategoryRequest;
import com.alember.my_warehouse.dto.category.CategoryResponse;
import com.alember.my_warehouse.enums.ApiStatus;
import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.mapper.CategoryMapper;
import com.alember.my_warehouse.services.CategoryServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alember.my_warehouse.dto.ApiResponse;
import com.alember.my_warehouse.model.CategoryModel;

import javax.xml.catalog.CatalogException;
import java.util.List;
import java.util.Optional;

/**
 * CategoryController handles CRUD operations for categories.
 *
 * Endpoints:
 * - POST /api/category/ - Create a new category.
 * - GET /api/category/ - Retrieve all categories.
 * - GET /api/category/{id} - Retrieve a category by ID.
 * - PUT /api/category/{id} - Update an existing category by ID.
 * - DELETE /api/category/{id} - Delete a category by ID.
 */
@RestController
@RequestMapping("/api/category")
@SuppressWarnings("unused")
public class CategoryController {

  @Autowired
  CategoryServices categoryServices;

  @Autowired
  CategoryMapper categoryMapper;

  /**
   * Creates a new category.
   * POST /api/category/
   * @param categoryRequest The category details.
   * @return ApiResponse with the status and created category.
   */
  @PostMapping("/")
  public ApiResponse createCategory(@RequestBody @Valid CategoryRequest categoryRequest) throws Exception {
    ApiResponse apiResponse = new ApiResponse();
    CategoryModel categoryModel = new CategoryModel();

    categoryModel.setName(categoryRequest.getName());
    categoryModel.setDescription(categoryRequest.getDescription());

    CategoryModel savedCategory = categoryServices.addCategory(categoryModel);

      apiResponse.setStatusCode(200);
      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("Category Added Successfully");
      apiResponse.setData(savedCategory);
      return apiResponse;
  }

  /**
   * Retrieves all categories.
   * GET /api/category/
   * @return ApiResponse with the list of categories.
   */
  @GetMapping("/")
  public ApiResponse getAllCategory() {
    ApiResponse apiResponse = new ApiResponse();
      List<CategoryModel> cat = categoryServices.getAllCategory();

    List<CategoryResponse> responseList = cat.stream().map(categoryMapper::toResponse).toList();

      apiResponse.setStatusCode(200);
      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("All category fetched successfully!");
      apiResponse.setData(responseList);
      return apiResponse;
  }

  /**
   * Updates an existing category.
   * PUT /api/category/{id}
   * @param id The category ID.
   * @param updatedCategory The updated category data.
   * @return ApiResponse with the status and updated category.
   */
  @PutMapping("/{id}")
  public ApiResponse updateCategory(@PathVariable("id") String id, @RequestBody CategoryRequest updatedCategory) {
    ApiResponse apiResponse = new ApiResponse();
    try {
      CategoryModel model = categoryMapper.toModel(updatedCategory);
      CategoryModel updated = categoryServices.updateCategory(id, model);
      CategoryResponse response = categoryMapper.toResponse(updated);

      apiResponse.setStatusCode(200);
      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("Category updated successfully!");
      apiResponse.setData(response);
    } catch (CategoryException e) {
      apiResponse.setStatusCode(500);
      apiResponse.setStatus(ApiStatus.ERROR);
      apiResponse.setDescription(e.getMessage());
    }
    return apiResponse;
  }

  /**
   * Retrieves a category by its ID.
   * GET /api/category/{id}
   * @param id The category ID.
   * @return ApiResponse with the category details.
   */
  @GetMapping("/{id}")
  public ApiResponse findCategoryById(@PathVariable("id") String id) {
    ApiResponse apiResponse = new ApiResponse();
    try {
      Optional<CategoryModel> categoryModelOptional = categoryServices.categoryById(id);

      // unwrap the optional model
      CategoryModel categoryModel= categoryModelOptional.orElseThrow(()-> new CatalogException("Category not found!"));

      CategoryResponse response = categoryMapper.toResponse(categoryModel);

      apiResponse.setStatusCode(200);
      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("Category fetched successfully!");
      apiResponse.setData(response);
    } catch (CategoryException e) {
      apiResponse.setStatusCode(500);
      apiResponse.setStatus(ApiStatus.ERROR);
      apiResponse.setDescription(e.getMessage());
    }
    return apiResponse;
  }

  /**
   * Deletes a category by its ID.
   * DELETE /api/category/{id}
   * @param id The category ID.
   * @return ApiResponse with the deletion status.
   */
  @DeleteMapping("/{id}")
  public ApiResponse deleteCategory(@PathVariable("id") String id) {
    ApiResponse apiResponse = new ApiResponse();
    try {
      categoryServices.deleteCategory(id);
      apiResponse.setStatusCode(200);
      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("Category deleted successfully");
    } catch (CategoryException e) {
      apiResponse.setStatusCode(500);
      apiResponse.setStatus(ApiStatus.ERROR);
      apiResponse.setDescription(e.getMessage());
    }
    return apiResponse;
  }
}
