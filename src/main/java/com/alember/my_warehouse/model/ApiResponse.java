package com.alember.my_warehouse.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponse {
	
	private String status;
	private String description;
	private String code;
	private Object data;

}
