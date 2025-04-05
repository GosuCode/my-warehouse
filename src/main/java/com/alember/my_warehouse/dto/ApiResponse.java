package com.alember.my_warehouse.dto;

import com.alember.my_warehouse.enums.ApiStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;

@Getter
@Setter
@ToString
public class ApiResponse {

  private ApiStatus status;
  private String description;
  private int statusCode;
  private Object data;
}
