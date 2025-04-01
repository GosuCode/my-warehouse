package com.alember.my_warehouse.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String categoryId;}
