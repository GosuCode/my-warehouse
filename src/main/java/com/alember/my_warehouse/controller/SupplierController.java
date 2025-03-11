package com.alember.my_warehouse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SupplierController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello Alember";
	}
}
