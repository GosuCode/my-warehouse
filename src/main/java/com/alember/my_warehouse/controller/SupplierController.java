package com.alember.my_warehouse.controller;

import com.alember.my_warehouse.dto.ApiResponse;
import com.alember.my_warehouse.enums.ApiStatus;
import com.alember.my_warehouse.exception.SupplierException;
import com.alember.my_warehouse.model.SupplierModel;
import com.alember.my_warehouse.services.SupplierServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

  @Autowired
  SupplierServices supplierService;
  /**
   * Creates a new supplier (following ProductController pattern).
   * POST /api/suppliers
   * Note: Assumes a @ControllerAdvice handles SupplierException for error responses.
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) // Keep consumes!
  public ApiResponse createSupplier(@RequestBody SupplierModel supplier) throws SupplierException {
    ApiResponse response = new ApiResponse();

    supplier.setId(null);
    supplier.setSuppliedProducts(null); // Or empty list if appropriate

    SupplierModel createdSupplier = supplierService.createSupplier(supplier);

    response.setStatus(ApiStatus.SUCCESS);
    response.setStatusCode(HttpStatus.CREATED.value());
    response.setDescription("Supplier created successfully.");
    response.setData(createdSupplier); // Set the saved supplier data

    return response;
  }

  /**
   * Gets all suppliers.
   * GET /api/suppliers
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse> getAllSuppliers() {
    ApiResponse response = new ApiResponse();
    try {
      List<SupplierModel> suppliers = supplierService.getAllSuppliers();
      response.setStatus(ApiStatus.SUCCESS);
      response.setStatusCode(HttpStatus.OK.value());
      response.setDescription("Suppliers fetched successfully.");
      response.setData(suppliers);
      return ResponseEntity.ok(response); // Shortcut for status OK
    } catch (Exception e) {
      response.setStatus(ApiStatus.ERROR);
      response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      response.setDescription("An unexpected error occurred while fetching suppliers.");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Gets a single supplier by ID.
   * GET /api/suppliers/{id}
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse> getSupplierById(@PathVariable String id) {
    ApiResponse response = new ApiResponse();
    return supplierService.getSupplierById(id)
            .map(supplier -> {
              response.setStatus(ApiStatus.SUCCESS);
              response.setStatusCode(HttpStatus.OK.value());
              response.setDescription("Supplier fetched successfully.");
              response.setData(supplier);
              return ResponseEntity.ok(response);
            })
            .orElseGet(() -> {
              response.setStatus(ApiStatus.ERROR);
              response.setStatusCode(HttpStatus.NOT_FOUND.value());
              response.setDescription("Supplier not found with id: " + id);
              return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            });
  }

  /**
   * Updates an existing supplier.
   * PUT /api/suppliers/{id}
   */
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse> updateSupplier(@PathVariable String id, @RequestBody SupplierModel supplierDetails) {
    ApiResponse response = new ApiResponse();
    try {
      SupplierModel updatedSupplier = supplierService.updateSupplier(id, supplierDetails);
      response.setStatus(ApiStatus.SUCCESS);
      response.setStatusCode(HttpStatus.OK.value());
      response.setDescription("Supplier updated successfully.");
      response.setData(updatedSupplier);
      return ResponseEntity.ok(response);
    } catch (SupplierException e) {
      // Check if the exception message indicates "not found" to return 404, else 400
      HttpStatus status = e.getMessage().toLowerCase().contains("not found") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
      response.setStatus(ApiStatus.ERROR);
      response.setStatusCode(status.value());
      response.setDescription(e.getMessage());
      return new ResponseEntity<>(response, status);
    } catch (Exception e) {
      response.setStatus(ApiStatus.ERROR);
      response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      response.setDescription("An unexpected error occurred while updating supplier.");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Deletes a supplier by ID.
   * DELETE /api/suppliers/{id}
   * WARNING: Also deletes associated products due to CascadeType.ALL.
   */
  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse> deleteSupplier(@PathVariable String id) {
    ApiResponse response = new ApiResponse();
    try {
      supplierService.deleteSupplier(id);
      response.setStatus(ApiStatus.SUCCESS);
      response.setStatusCode(HttpStatus.OK.value()); // Or HttpStatus.NO_CONTENT.value() if no body is needed
      response.setDescription("Supplier deleted successfully.");
      // response.setData(null); // Optional: explicitly set data to null
      return ResponseEntity.ok(response); // Return 200 OK with confirmation message
      // Alternatively, for 204 No Content:
      // return ResponseEntity.noContent().build();
    } catch (SupplierException e) {
      response.setStatus(ApiStatus.ERROR);
      response.setStatusCode(HttpStatus.NOT_FOUND.value()); // Assume delete failure is usually due to not found
      response.setDescription(e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      response.setStatus(ApiStatus.ERROR);
      response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      response.setDescription("An unexpected error occurred while deleting supplier.");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
