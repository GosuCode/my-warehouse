package com.alember.my_warehouse.dto.category;

import com.alember.my_warehouse.model.ProductModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryResponse {
    private String id;
    private String name;
    private String description;
    private List<ProductModel> products;
}
