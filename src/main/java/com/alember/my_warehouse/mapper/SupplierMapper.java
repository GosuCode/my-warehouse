package com.alember.my_warehouse.mapper;

import com.alember.my_warehouse.dto.product.ProductResponse;
import com.alember.my_warehouse.dto.supplier.SupplierResponse;
import com.alember.my_warehouse.model.SupplierModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public SupplierResponse toResponse(SupplierModel model){
        SupplierResponse response = new SupplierResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        response.setId(model.getId());
        response.setName(model.getName());
        response.setPhone(model.getPhone());
        response.setEmail(model.getEmail());
        response.setAddress(model.getAddress());
        response.setSuppliedProducts(model.getSuppliedProducts());

        return response;
    }
}
