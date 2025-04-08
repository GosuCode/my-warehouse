package com.alember.my_warehouse.mapper;

import com.alember.my_warehouse.dto.product.ProductResponse;
import com.alember.my_warehouse.model.ProductModel;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(ProductModel model){
        ProductResponse response = new ProductResponse();
        response.setId(model.getId());
        response.setSku(model.getSku());
        response.setName(model.getName());
        response.setDescription(model.getDescription());
        response.setPrice(model.getPrice());
        response.setQuantity(model.getQuantity());

        if (model.getCategory() != null) {
            response.setCategoryId(model.getCategory().getId());
        }

        if (model.getSupplier() != null) {
            response.setSupplierId(model.getSupplier().getId());
        }

        return response;
    }
}
