package com.alember.my_warehouse.dto.category;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
