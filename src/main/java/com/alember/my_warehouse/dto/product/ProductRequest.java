package com.alember.my_warehouse.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    @NotBlank
    private String sku;

    @NotBlank
    private String name;

    private String description;

    @Positive
    private Double price;

    @Min(0)
    private Integer quantity;

    @NotBlank
    private String categoryId;

    private String supplierId;
}
