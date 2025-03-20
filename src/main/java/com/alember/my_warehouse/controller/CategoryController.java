package com.alember.my_warehouse.controller;

import com.alember.my_warehouse.enums.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.alember.my_warehouse.dto.ApiResponse;
import com.alember.my_warehouse.model.CategoryModel;
import com.alember.my_warehouse.services.impl.CategoryImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	@Autowired
	CategoryImpl catImpl;

	@Autowired
	ApiResponse apiResponse;
	
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ApiResponse addCategory(@RequestBody CategoryModel categoryModel) {

		try {
			CategoryModel cat =  catImpl.addCategory(categoryModel);
			apiResponse.setStatusCode(200);
			apiResponse.setStatus(ApiStatus.SUCCESS);
			apiResponse.setDescription("Category Added Successfully");
			apiResponse.setData(cat);
		} catch (Exception e) {
			apiResponse.setStatusCode(500);
			apiResponse.setStatus(ApiStatus.ERROR);
			apiResponse.setDescription(e.getMessage());
		}
		return apiResponse;
	}

	@GetMapping("/all-category")
	public ApiResponse getAllCategory(){
		try{
			List<CategoryModel> cat = catImpl.getAllCategory();
			apiResponse.setStatusCode(200);
			apiResponse.setStatus(ApiStatus.SUCCESS);
			apiResponse.setDescription("All category fetched successfully!");
			apiResponse.setData(cat);
		}catch (Exception e){
			apiResponse.setStatusCode(500);
			apiResponse.setStatus(ApiStatus.ERROR);
			apiResponse.setDescription(e.getMessage());
		}
		return apiResponse;
	}

	@PutMapping("/update-category/{id}")
	public ApiResponse updateCategory(@PathVariable("id") String id, @RequestBody CategoryModel category){
		try{
			catImpl.updateCategory(id, category);
			apiResponse.setStatusCode(200);
			apiResponse.setStatus(ApiStatus.SUCCESS);
			apiResponse.setDescription("Category updated successfully!");
			apiResponse.setData(category);
		}catch (Exception e){
			apiResponse.setStatusCode(500);
			apiResponse.setStatus(ApiStatus.ERROR);
			apiResponse.setDescription(e.getMessage());
		}
		return apiResponse;
	}

	@GetMapping("/find-category/{id}")
	public ApiResponse findCategoryById(@PathVariable("id") String id){
		try {
			Optional<CategoryModel> cat = catImpl.findById(id);
			apiResponse.setStatusCode(200);
			apiResponse.setStatus(ApiStatus.SUCCESS);
			apiResponse.setDescription("Category updated successfully!");
			apiResponse.setData(cat);
		} catch (Exception e){
			apiResponse.setStatusCode(500);
			apiResponse.setStatus(ApiStatus.ERROR);
			apiResponse.setDescription(e.getMessage());
		}
		return apiResponse;
	}


	@DeleteMapping("/delete/{id}")
	public ApiResponse deleteCategory(@PathVariable("id") String id) {
		try {
			catImpl.deleteCategory(id);
			apiResponse.setStatusCode(200);
			apiResponse.setStatus(ApiStatus.SUCCESS);
			apiResponse.setDescription("Category deleted successfully");
		}catch (Exception e){
			apiResponse.setStatusCode(500);
			apiResponse.setStatus(ApiStatus.ERROR);
			apiResponse.setDescription(e.getMessage());
		}
		return apiResponse;
	}
}
