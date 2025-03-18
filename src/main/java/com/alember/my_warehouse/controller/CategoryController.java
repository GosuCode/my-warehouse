package com.alember.my_warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alember.my_warehouse.model.ApiResponse;
import com.alember.my_warehouse.model.CategoryModel;
import com.alember.my_warehouse.services.impl.CategoryImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	@Autowired
	CategoryImpl catImpl;
	
	@PostMapping("/create")
	@ResponseBody
	public ApiResponse addCategory(@RequestBody CategoryModel category) {
		
		ApiResponse apiResponse = new ApiResponse();
		
		try {
			CategoryModel catogery =  catImpl.addCategory(category);
			apiResponse.setCode("200");
			apiResponse.setStatus("success");
			apiResponse.setDescription("Category Added Successfully");
			apiResponse.setData(catogery);
		} catch (Exception e) {
			apiResponse.setCode("500");
			apiResponse.setStatus("Error");
			apiResponse.setDescription(e.getMessage());
		
			
		}
		return apiResponse;
	}
}
