package com.alember.my_warehouse.controller;

import com.alember.my_warehouse.dto.ApiResponse;
import com.alember.my_warehouse.dto.ProductRequest;
import com.alember.my_warehouse.enums.ApiStatus;
import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.exception.ProductNotFoundException;
import com.alember.my_warehouse.model.ProductModel;
import com.alember.my_warehouse.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Autowired
  ProductServices productServices;

  @PostMapping("/create")
  public ApiResponse createProduct(@RequestBody ProductRequest productRequest) throws CategoryException {
    ApiResponse apiResponse = new ApiResponse();
    ProductModel productModel = new ProductModel();
    productModel.setSku(productRequest.getSku());
    productModel.setName(productRequest.getName());
    productModel.setDescription(productRequest.getDescription());
    productModel.setPrice(productRequest.getPrice());
    productModel.setQuantity(productRequest.getQuantity());

    ProductModel savedProduct = productServices.addProduct(productModel, productRequest.getCategoryId());

    apiResponse.setStatus(ApiStatus.SUCCESS);
    apiResponse.setDescription("Product created successfully!");
    apiResponse.setData(savedProduct);
    return apiResponse;
  }

  @GetMapping("/all")
  public ApiResponse getAllProduct(){
    ApiResponse apiResponse = new ApiResponse();
    List<ProductModel> allProducts = productServices.getAllProduct();

    apiResponse.setStatus(ApiStatus.SUCCESS);
    apiResponse.setDescription("Product created successfully!");
    apiResponse.setData(allProducts);
    return apiResponse;
  }


  @GetMapping("/get/{id}")
  public ApiResponse getSingleProduct(@PathVariable("id") String id){
    ApiResponse apiResponse = new ApiResponse();
    try{
      List<ProductModel> allProducts = productServices.getAllProduct();
      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("Product created successfully!");
      apiResponse.setData(allProducts);
    }catch (ProductNotFoundException e){
      apiResponse.setStatusCode(500);
      apiResponse.setStatus(ApiStatus.ERROR);
      apiResponse.setDescription(e.getMessage());
    }
    return apiResponse;
  }

}
