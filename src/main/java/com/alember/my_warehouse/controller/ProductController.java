package com.alember.my_warehouse.controller;

import com.alember.my_warehouse.dto.ApiResponse;
import com.alember.my_warehouse.dto.product.ProductRequest;
import com.alember.my_warehouse.dto.product.ProductResponse;
import com.alember.my_warehouse.enums.ApiStatus;
import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.exception.ProductNotFoundException;
import com.alember.my_warehouse.exception.SupplierException;
import com.alember.my_warehouse.mapper.ProductMapper;
import com.alember.my_warehouse.model.ProductModel;
import com.alember.my_warehouse.services.ProductServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ProductController handles CRUD operations for products.
 *
 * Endpoints:
 * - POST /api/product/ - Create a new product.
 * - GET /api/product/ - Retrieve all products.
 * - GET /api/product/{id} - Retrieve a product by ID.
 * - PUT /api/product/{id} - Update an existing product by ID.
 * - DELETE /api/product/{id} - Delete a product by ID.
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Autowired
  ProductServices productServices;

  @Autowired
  private ProductMapper productMapper;

  /**
   * Creates a new product.
   * POST /api/product/
   * @param productRequest The product details.
   * @return ApiResponse with the status and created product.
   */
  @PostMapping("/")
  public ApiResponse createProduct(@RequestBody @Valid ProductRequest productRequest) throws CategoryException {
    ApiResponse apiResponse = new ApiResponse();
    ProductModel productModel = new ProductModel();

    productModel.setSku(productRequest.getSku());
    productModel.setName(productRequest.getName());
    productModel.setDescription(productRequest.getDescription());
    productModel.setPrice(productRequest.getPrice());
    productModel.setQuantity(productRequest.getQuantity());

    ProductModel savedProduct = productServices.addProduct(productModel, productRequest.getCategoryId(), productRequest.getSupplierId());

    ProductResponse response = productMapper.toResponse(savedProduct);

    apiResponse.setStatus(ApiStatus.SUCCESS);
    apiResponse.setDescription("Product created successfully!");
    apiResponse.setData(response);
    return apiResponse;
  }

  /**
   * Retrieves all products.
   * GET /api/product/
   * @return ApiResponse with the list of products.
   */
  @GetMapping("/")
  public ApiResponse getAllProduct(){
    ApiResponse apiResponse = new ApiResponse();
    List<ProductModel> allProducts = productServices.getAllProduct();

    List<ProductResponse> responseList = allProducts.stream().map(productMapper::toResponse).toList();

    apiResponse.setStatus(ApiStatus.SUCCESS);
    apiResponse.setDescription("Products created successfully!");
    apiResponse.setData(responseList);
    return apiResponse;
  }

  /**
   * Retrieves a product by its ID.
   * GET /api/product/{id}
   * @param id The product ID.
   * @return ApiResponse with the product details.
   */
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

  /**
   * Updates an existing product.
   * PUT /api/product/{id}
   * @param id The product ID.
   * @param productRequest The updated product data.
   * @return ApiResponse with the status and updated product.
   */
  @PutMapping("/{id}")
  public ApiResponse updateProduct(@PathVariable("id") String id, @RequestBody @Valid ProductRequest productRequest) {
    ApiResponse apiResponse = new ApiResponse();
    try {
      ProductModel updatedProduct = productServices.updateProduct(id, productRequest);
      ProductResponse response = productMapper.toResponse(updatedProduct);

      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("Product updated successfully!");
      apiResponse.setData(response);
    } catch (ProductNotFoundException | CategoryException | SupplierException e) {
      apiResponse.setStatus(ApiStatus.ERROR);
      apiResponse.setStatusCode(404);
      apiResponse.setDescription(e.getMessage());
    }
    return apiResponse;
  }

  /**
   * Deletes a product by its ID.
   * DELETE /api/product/{id}
   * @param id The product ID.
   * @return ApiResponse with the deletion status.
   */
  @DeleteMapping("/{id}")
  public ApiResponse deleteProduct(@PathVariable("id") String id) {
    ApiResponse apiResponse = new ApiResponse();
    try {
      productServices.deleteProduct(id);
      apiResponse.setStatus(ApiStatus.SUCCESS);
      apiResponse.setDescription("Product deleted successfully!");
    } catch (ProductNotFoundException e) {
      apiResponse.setStatus(ApiStatus.ERROR);
      apiResponse.setStatusCode(404);
      apiResponse.setDescription(e.getMessage());
    }
    return apiResponse;
  }

}
