package com.alember.my_warehouse.controller;

import com.alember.my_warehouse.dto.CategoryRequest;
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

@RestController
@RequestMapping("/api/category")
public class CategoryController {

  @Autowired
  CategoryServices categoryServices;

  @Autowired
  CategoryMapper categoryMapper;

  @PostMapping("/")
  public ApiResponse createCategory(@RequestBody @Valid CategoryRequest categoryRequest) throws Exception {
    ApiResponse apiResponse = new ApiResponse();
    CategoryModel categoryModel = new CategoryModel();

    categoryModel.setName(categoryRequest.getName());
    categoryModel.setDescription(categoryRequest.getDescription());

    CategoryModel savedCategory = categoryServices.addCategory(categoryModel);

    CategoryResponse response = categoryMapper.toResponse(savedCategory);

      apiResponse.setStatusCode(200);
      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("Category Added Successfully");
      apiResponse.setData(response);
      return apiResponse;
  }

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

  @PutMapping("/{id}")
  public ApiResponse updateCategory(@PathVariable("id") String id, @RequestBody CategoryModel updatedCategory) {
    ApiResponse apiResponse = new ApiResponse();
    try {
      categoryServices.updateCategory(id, updatedCategory);
      apiResponse.setStatusCode(200);
      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("Category updated successfully!");
      apiResponse.setData(updatedCategory);
    } catch (CategoryException e) {
      apiResponse.setStatusCode(500);
      apiResponse.setStatus(ApiStatus.ERROR);
      apiResponse.setDescription(e.getMessage());
    }
    return apiResponse;
  }

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
      apiResponse.setDescription("Category updated successfully!");
      apiResponse.setData(response);
    } catch (CategoryException e) {
      apiResponse.setStatusCode(500);
      apiResponse.setStatus(ApiStatus.ERROR);
      apiResponse.setDescription(e.getMessage());
    }
    return apiResponse;
  }

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
