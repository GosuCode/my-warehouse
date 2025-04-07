package com.alember.my_warehouse.mapper;

import com.alember.my_warehouse.dto.category.CategoryResponse;
import com.alember.my_warehouse.model.CategoryModel;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(CategoryModel model){
        CategoryResponse response = new CategoryResponse();

        response.setId(model.getId());
        response.setName(model.getName());
        response.setDescription(model.getDescription());
        response.setProducts(model.getProducts());

        return response;
    }
}
