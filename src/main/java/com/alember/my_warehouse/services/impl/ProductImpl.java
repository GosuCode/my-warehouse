package com.alember.my_warehouse.services.impl;

import com.alember.my_warehouse.dto.product.ProductRequest;
import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.exception.ProductNotFoundException;
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

    @Override
    public ProductModel updateProduct(String id, ProductRequest request)
            throws ProductNotFoundException, CategoryException {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        if (request.getCategoryId() != null) {
            CategoryModel category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new CategoryException("Category not found with ID: " + request.getCategoryId()));
            product.setCategory(category);
        }

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }
}
