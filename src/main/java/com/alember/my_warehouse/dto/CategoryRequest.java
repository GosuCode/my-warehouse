package com.alember.my_warehouse.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CategoryRequest {
    private String name;
    private String description;
}
