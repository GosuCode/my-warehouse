package com.alember.my_warehouse.dto.supplier;

import com.alember.my_warehouse.model.ProductModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SupplierResponse {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private List<ProductModel> suppliedProducts;
}
