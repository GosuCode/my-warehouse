package com.alember.my_warehouse.exception;

public class ProductNotFoundException extends RuntimeException{

    ProductNotFoundException(String message){
        super(message);
    }

}
