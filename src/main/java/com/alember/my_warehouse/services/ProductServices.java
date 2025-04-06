package com.alember.my_warehouse.services;

import com.alember.my_warehouse.dto.product.ProductRequest;
import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.exception.ProductNotFoundException;
import com.alember.my_warehouse.model.ProductModel;

import java.util.List;
import java.util.Optional;

public interface ProductServices {
    ProductModel addProduct(ProductModel product, String categoryId) throws CategoryException;
    List<ProductModel> getAllProduct();
    Optional<ProductModel> getProduct(String id);

    ProductModel updateProduct(String id, ProductRequest request) throws ProductNotFoundException, CategoryException;
    void deleteProduct(String id) throws ProductNotFoundException;
}
