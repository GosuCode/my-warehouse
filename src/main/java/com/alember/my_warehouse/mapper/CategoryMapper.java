package com.alember.my_warehouse.mapper;

import com.alember.my_warehouse.dto.category.CategoryResponse;
import com.alember.my_warehouse.dto.product.ProductResponse;
import com.alember.my_warehouse.model.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    @Autowired
    ProductMapper productMapper;

    public CategoryResponse toResponse(CategoryModel model){
        CategoryResponse response = new CategoryResponse();

        response.setId(model.getId());
        response.setName(model.getName());
        response.setDescription(model.getDescription());

        // Convert each ProductModel to ProductResponse
        List<ProductResponse> products = model.getProducts().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());

        response.setProducts(products);

        return response;
    }
}
