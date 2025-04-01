package com.alember.my_warehouse.exception;

import com.alember.my_warehouse.dto.ApiResponse;
import com.alember.my_warehouse.enums.ApiStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handleProductNotFoundException(ProductNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(ApiStatus.ERROR);
        apiResponse.setDescription(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleGenericException(Exception ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(ApiStatus.ERROR);
        apiResponse.setDescription("An unexpected error occurred: " + ex.getMessage());
        return apiResponse;
    }
}
