package com.alember.my_warehouse.controller;

import com.alember.my_warehouse.dto.ApiResponse;
import com.alember.my_warehouse.dto.product.ProductRequest;
import com.alember.my_warehouse.dto.product.ProductResponse;
import com.alember.my_warehouse.enums.ApiStatus;
import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.exception.ProductNotFoundException;
import com.alember.my_warehouse.mapper.ProductMapper;
import com.alember.my_warehouse.model.ProductModel;
import com.alember.my_warehouse.services.ProductServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Autowired
  ProductServices productServices;

  @Autowired
  private ProductMapper productMapper;

  @PostMapping("/")
  public ApiResponse createProduct(@RequestBody @Valid ProductRequest productRequest) throws CategoryException {
    ApiResponse apiResponse = new ApiResponse();
    ProductModel productModel = new ProductModel();
    productModel.setSku(productRequest.getSku());
    productModel.setName(productRequest.getName());
    productModel.setDescription(productRequest.getDescription());
    productModel.setPrice(productRequest.getPrice());
    productModel.setQuantity(productRequest.getQuantity());

    ProductModel savedProduct = productServices.addProduct(productModel, productRequest.getCategoryId());

    ProductResponse response = productMapper.toResponse(savedProduct);

    apiResponse.setStatus(ApiStatus.SUCCESS);
    apiResponse.setDescription("Product created successfully!");
    apiResponse.setData(savedProduct);
    return apiResponse;
  }


  @GetMapping("/")
  public ApiResponse getAllProduct(){
    ApiResponse apiResponse = new ApiResponse();
    List<ProductModel> allProducts = productServices.getAllProduct();

    List<ProductResponse> responseList = allProducts.stream().map(productMapper::toResponse).toList();

    apiResponse.setStatus(ApiStatus.SUCCESS);
    apiResponse.setDescription("Products created successfully!");
    apiResponse.setData(allProducts);
    return apiResponse;
  }


  @GetMapping("/{id}")
  public ApiResponse getSingleProduct(@PathVariable("id") String id){
    ApiResponse apiResponse = new ApiResponse();
    try{
      Optional<ProductModel> productModelOptional = productServices.getProduct(id);

      // unwrap the optional model
      ProductModel productModel = productModelOptional.orElseThrow(()-> new ProductNotFoundException("Product not found!"));

      ProductResponse response = productMapper.toResponse(productModel);

      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("Product created successfully!");
      apiResponse.setData(response);
    }catch (ProductNotFoundException e){
      apiResponse.setStatusCode(500);
      apiResponse.setStatus(ApiStatus.ERROR);
      apiResponse.setDescription(e.getMessage());
    }
    return apiResponse;
  }

}
