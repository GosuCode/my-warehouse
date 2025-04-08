package com.alember.my_warehouse.dto.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;

}
