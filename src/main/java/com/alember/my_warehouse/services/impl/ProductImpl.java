package com.alember.my_warehouse.services.impl;

import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.model.CategoryModel;
import com.alember.my_warehouse.model.ProductModel;
import com.alember.my_warehouse.repository.CategoryRepository;
import com.alember.my_warehouse.repository.ProductRepository;
import com.alember.my_warehouse.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImpl implements ProductServices {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ProductModel addProduct(ProductModel product, String categoryId) throws CategoryException {
        CategoryModel category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryException("Category not found"));

        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public List<ProductModel> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Optional<ProductModel> getProduct(String id) {
        Optional<ProductModel> product = productRepository.findById(id);
        return product;
    }
}
