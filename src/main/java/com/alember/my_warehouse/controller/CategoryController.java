package com.alember.my_warehouse.controller;

import com.alember.my_warehouse.enums.ApiStatus;
import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.alember.my_warehouse.dto.ApiResponse;
import com.alember.my_warehouse.model.CategoryModel;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

  @Autowired
  CategoryServices categoryServices;

  @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ApiResponse addCategory(@RequestBody CategoryModel categoryModel) {
    ApiResponse apiResponse = new ApiResponse();
    try {
      CategoryModel cat = categoryServices.addCategory(categoryModel);
      apiResponse.setStatusCode(200);
      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("Category Added Successfully");
      apiResponse.setData(cat);
    } catch (CategoryException e) {
      apiResponse.setStatusCode(500);
      apiResponse.setStatus(ApiStatus.ERROR);
      apiResponse.setDescription(e.getMessage());
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
      return apiResponse;
  }

  @GetMapping("/all")
  public ApiResponse getAllCategory() {
    ApiResponse apiResponse = new ApiResponse();
      List<CategoryModel> cat = categoryServices.getAllCategory();
      apiResponse.setStatusCode(200);
      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("All category fetched successfully!");
      apiResponse.setData(cat);
      return apiResponse;
  }

  @PutMapping("/update/{id}")
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

  @GetMapping("/get/{id}")
  public ApiResponse findCategoryById(@PathVariable("id") String id) {
    ApiResponse apiResponse = new ApiResponse();
    try {
      Optional<CategoryModel> cat = categoryServices.findById(id);
      apiResponse.setStatusCode(200);
      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("Category updated successfully!");
      apiResponse.setData(cat);
    } catch (CategoryException e) {
      apiResponse.setStatusCode(500);
      apiResponse.setStatus(ApiStatus.ERROR);
      apiResponse.setDescription(e.getMessage());
    }
    return apiResponse;
  }

  @DeleteMapping("/delete/{id}")
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
