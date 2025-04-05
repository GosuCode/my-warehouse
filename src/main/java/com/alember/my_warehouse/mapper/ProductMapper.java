package com.alember.my_warehouse.mapper;

import com.alember.my_warehouse.dto.product.ProductResponse;
import com.alember.my_warehouse.model.ProductModel;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(ProductModel model){
        ProductResponse response = new ProductResponse();
        response.setSku(model.getSku());
        response.setName(model.getName());
        response.setDescription(model.getDescription());
        response.setPrice(model.getPrice());
        response.setQuantity(model.getQuantity());

        response.setCategoryId(model.getCategory().getId().toString());

        return response;
    }
}
