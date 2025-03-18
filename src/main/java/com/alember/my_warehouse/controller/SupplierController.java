package com.alember.my_warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alember.my_warehouse.services.impl.SupplierImpl;

@RestController
@RequestMapping("/api")
public class SupplierController {
	
	@Autowired
	SupplierImpl supplierImpl;

	@GetMapping("/hello")
	public String hello() {
		return "Hello Alember";
	}
	
	
}
