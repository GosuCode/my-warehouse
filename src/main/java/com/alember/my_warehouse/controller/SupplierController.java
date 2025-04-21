package com.alember.my_warehouse.controller;

import com.alember.my_warehouse.dto.ApiResponse;
import com.alember.my_warehouse.dto.supplier.SupplierRequest;
import com.alember.my_warehouse.dto.supplier.SupplierResponse;
import com.alember.my_warehouse.enums.ApiStatus;
import com.alember.my_warehouse.exception.SupplierException;
import com.alember.my_warehouse.mapper.SupplierMapper;
import com.alember.my_warehouse.model.SupplierModel;
import com.alember.my_warehouse.services.SupplierServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@SuppressWarnings("unused")
public class SupplierController {

  @Autowired
  SupplierServices supplierService;

  @Autowired
  SupplierMapper supplierMapper;

  /**
   * Creates a new supplier.
   * POST /api/suppliers
   */
  @PostMapping("/")
  public ApiResponse createSupplier(@RequestBody @Valid SupplierRequest supplierRequest) throws SupplierException {
    ApiResponse response = new ApiResponse();

    SupplierModel supplierModel = new SupplierModel();

    supplierModel.setName(supplierRequest.getName());
    supplierModel.setPhone(supplierRequest.getPhone());
    supplierModel.setEmail(supplierRequest.getEmail());
    supplierModel.setAddress(supplierRequest.getAddress());

    SupplierModel saveSupplier = supplierService.createSupplier(supplierModel);

    SupplierResponse supplierResponse = supplierMapper.toResponse(saveSupplier);

    response.setStatus(ApiStatus.SUCCESS);
    response.setStatusCode(HttpStatus.CREATED.value());
    response.setDescription("Supplier created successfully.");
    response.setData(supplierResponse);

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
      List<SupplierResponse> responseList = suppliers.stream()
              .map(supplierMapper::toResponse)
              .toList();

      response.setStatus(ApiStatus.SUCCESS);
      response.setStatusCode(HttpStatus.OK.value());
      response.setDescription("Suppliers fetched successfully.");
      response.setData(responseList);
      return ResponseEntity.ok(response);
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
              SupplierResponse supplierResponse = supplierMapper.toResponse(supplier);
              response.setStatus(ApiStatus.SUCCESS);
              response.setStatusCode(HttpStatus.OK.value());
              response.setDescription("Supplier fetched successfully.");
              response.setData(supplierResponse);
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
  @PutMapping(value = "/{id}")
  public ResponseEntity<ApiResponse> updateSupplier(@PathVariable String id, @RequestBody SupplierRequest supplierRequest) {
    ApiResponse response = new ApiResponse();
    try {
      SupplierModel supplierModel = new SupplierModel();
      supplierModel.setName(supplierRequest.getName());
      supplierModel.setPhone(supplierRequest.getPhone());
      supplierModel.setEmail(supplierRequest.getEmail());
      supplierModel.setAddress(supplierRequest.getAddress());

      SupplierModel updatedSupplier = supplierService.updateSupplier(id, supplierModel);
      SupplierResponse supplierResponse = supplierMapper.toResponse(updatedSupplier);

      response.setStatus(ApiStatus.SUCCESS);
      response.setStatusCode(HttpStatus.OK.value());
      response.setDescription("Supplier updated successfully.");
      response.setData(supplierResponse);
      return ResponseEntity.ok(response);
    } catch (SupplierException e) {
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
      response.setStatusCode(HttpStatus.OK.value());
      response.setDescription("Supplier deleted successfully.");
       response.setData(null);
      return ResponseEntity.ok(response);
    } catch (SupplierException e) {
      response.setStatus(ApiStatus.ERROR);
      response.setStatusCode(HttpStatus.NOT_FOUND.value());
      response.setDescription(e.getMessage());
      response.setData(null);
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      response.setStatus(ApiStatus.ERROR);
      response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      response.setDescription("An unexpected error occurred while deleting supplier.");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
