package com.alember.my_warehouse.services.impl;

import com.alember.my_warehouse.dto.product.ProductRequest;
import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.exception.ProductNotFoundException;
import com.alember.my_warehouse.exception.SupplierException;
import com.alember.my_warehouse.model.CategoryModel;
import com.alember.my_warehouse.model.ProductModel;
import com.alember.my_warehouse.model.SupplierModel;
import com.alember.my_warehouse.repository.CategoryRepository;
import com.alember.my_warehouse.repository.ProductRepository;
import com.alember.my_warehouse.repository.SupplierRepository;
import com.alember.my_warehouse.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ProductImpl implements ProductServices to handle business logic for products.
 *
 * Methods:
 * - addProduct: Adds a new product, associating it with a category.
 * - getAllProduct: Retrieves all products.
 * - getProduct: Retrieves a product by its ID.
 * - updateProduct: Updates an existing product by ID.
 * - deleteProduct: Deletes a product by ID.
 */
@Service
public class ProductImpl implements ProductServices {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    /**
     * Adds a new product and associates it with a category.
     * @param product The product to be added.
     * @param categoryId The ID of the category to associate.
     * @return The saved product.
     * @throws CategoryException If the category does not exist.
     */
    @Override
    public ProductModel addProduct(ProductModel product, String categoryId, String supplierId) throws CategoryException {
        CategoryModel category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryException("Category not found."));

        SupplierModel supplier= supplierRepository.findById(supplierId).orElseThrow(() -> new SupplierException("Supplier not found."));

        product.setCategory(category);
        product.setSupplier(supplier);
        return productRepository.save(product);
    }

    /**
     * Retrieves all products.
     * @return List of all products.
     */
    @Override
    public List<ProductModel> getAllProduct() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its ID.
     * @param id The product ID.
     * @return The product if found.
     */
    @Override
    public Optional<ProductModel> getProduct(String id) {
        Optional<ProductModel> product = productRepository.findById(id);
        return product;
    }

    /**
     * Updates an existing product by its ID.
     * @param id The product ID.
     * @param request The updated product data.
     * @return The updated product.
     * @throws ProductNotFoundException If the product is not found.
     * @throws CategoryException If the category does not exist.
     */
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

        if (request.getSupplierId() != null) {
            SupplierModel supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new SupplierException("Supplier not found with ID: " + request.getSupplierId()));
            product.setSupplier(supplier);
        }

        return productRepository.save(product);
    }

    /**
     * Deletes a product by its ID.
     * @param id The product ID.
     * @throws ProductNotFoundException If the product does not exist.
     */
    @Override
    public void deleteProduct(String id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }
}
