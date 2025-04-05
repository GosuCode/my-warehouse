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

@Service
public class SupplierImpl implements SupplierServices{

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    @Transactional // Ensures the operation is atomic
    public SupplierModel createSupplier(SupplierModel supplier) throws SupplierException {
        // Optional: Add basic validation
        if (supplier.getName() == null || supplier.getName().isBlank()) {
            throw new SupplierException("Supplier name cannot be empty.");
        }
        if (supplier.getEmail() == null || supplier.getEmail().isBlank()) {
            throw new SupplierException("Supplier email cannot be empty.");
        }
        // Could add checks for existing email/phone if they should be unique

        // Ensure ID is null for creation to avoid accidental updates
        supplier.setId(null);
        return supplierRepository.save(supplier);
    }

    @Override
    @Transactional(readOnly = true) // Optimization for read operations
    public List<SupplierModel> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SupplierModel> getSupplierById(String id) {
        return supplierRepository.findById(id);
    }

    @Override
    @Transactional
    public SupplierModel updateSupplier(String id, SupplierModel supplierDetails) throws SupplierException {
        SupplierModel existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierException("Supplier not found with id: " + id));

        // Update only the fields that should be updatable
        existingSupplier.setName(supplierDetails.getName());
        existingSupplier.setPhone(supplierDetails.getPhone());
        existingSupplier.setEmail(supplierDetails.getEmail());
        existingSupplier.setAddress(supplierDetails.getAddress());
        // DO NOT typically update the list of products directly here.
        // Product association is usually managed from the Product side.

        return supplierRepository.save(existingSupplier);
    }

    @Override
    @Transactional
    public void deleteSupplier(String id) throws SupplierException {
        if (!supplierRepository.existsById(id)) {
            throw new SupplierException("Supplier not found with id: " + id);
        }
        // WARNING: CascadeType.ALL will delete associated products!
        supplierRepository.deleteById(id);
    }
}
