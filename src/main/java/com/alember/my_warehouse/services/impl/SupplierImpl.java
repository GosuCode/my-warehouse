package com.alember.my_warehouse.services.impl;

import com.alember.my_warehouse.exception.SupplierException;
import com.alember.my_warehouse.model.SupplierModel;
import com.alember.my_warehouse.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alember.my_warehouse.services.SupplierServices;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * SupplierImpl implements SupplierServices to handle business logic for suppliers.
 *
 * Methods:
 * - createSupplier: Creates a new supplier after validating required fields.
 * - getAllSuppliers: Retrieves all suppliers.
 * - getSupplierById: Retrieves a supplier by ID.
 * - updateSupplier: Updates an existing supplier by ID.
 * - deleteSupplier: Deletes a supplier by ID.
 */
@Service
public class SupplierImpl implements SupplierServices{

    @Autowired
    SupplierRepository supplierRepository;

    /**
     * Creates a new supplier after validating the supplier's name and email.
     * @param supplier The supplier to be created.
     * @return The saved supplier.
     * @throws SupplierException If the supplier's name or email is empty.
     */
    @Override
    @Transactional // Ensures the operation is atomic
    public SupplierModel createSupplier(SupplierModel supplier) throws SupplierException {
        if (supplier.getName() == null || supplier.getName().isBlank()) {
            throw new SupplierException("Supplier name cannot be empty.");
        }
        if (supplier.getEmail() == null || supplier.getEmail().isBlank()) {
            throw new SupplierException("Supplier email cannot be empty.");
        }

        supplier.setId(null);
        return supplierRepository.save(supplier);
    }

    /**
     * Retrieves all suppliers.
     * @return List of all suppliers.
     */
    @Override
    @Transactional(readOnly = true) // Optimization for read operations
    public List<SupplierModel> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    /**
     * Retrieves a supplier by its ID.
     * @param id The supplier ID.
     * @return The supplier if found.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplierModel> getSupplierById(String id) {
        return supplierRepository.findById(id);
    }

    /**
     * Updates an existing supplier by its ID.
     * @param id The supplier ID.
     * @param supplierDetails The updated supplier data.
     * @return The updated supplier.
     * @throws SupplierException If the supplier is not found.
     */
    @Override
    @Transactional
    public SupplierModel updateSupplier(String id, SupplierModel supplierDetails) throws SupplierException {
        SupplierModel existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierException("Supplier not found with id: " + id));

        existingSupplier.setName(supplierDetails.getName());
        existingSupplier.setPhone(supplierDetails.getPhone());
        existingSupplier.setEmail(supplierDetails.getEmail());
        existingSupplier.setAddress(supplierDetails.getAddress());

        return supplierRepository.save(existingSupplier);
    }

    /**
     * Deletes a supplier by its ID.
     * @param id The supplier ID.
     * @throws SupplierException If the supplier is not found.
     */
    @Override
    @Transactional
    public void deleteSupplier(String id) throws SupplierException {
        if (!supplierRepository.existsById(id)) {
            throw new SupplierException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }
}
