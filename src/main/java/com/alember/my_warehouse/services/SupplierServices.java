package com.alember.my_warehouse.services;


import com.alember.my_warehouse.exception.SupplierException;
import com.alember.my_warehouse.model.SupplierModel;

import java.util.List;
import java.util.Optional;

public interface SupplierServices {

    /**
     * Creates a new supplier
     *
     * @param supplier The supplier data to create.
     * @return The created supplier with its generated ID.
     * @throws SupplierException If validation fails or another issue occurs.*
     * */
    SupplierModel createSupplier(SupplierModel supplier) throws SupplierException;

    /**
     * Retrieves a list of all suppliers.
     *
     * @return A list of all suppliers.
     */
    List<SupplierModel> getAllSuppliers();

    /**
     * Retrieves a supplier by its unique ID.
     *
     * @param id The ID of the supplier to retrieve.
     * @return An Optional containing the supplier if found, otherwise empty.
     */
    Optional<SupplierModel> getSupplierById(String id);

    /**
     * Updates an existing supplier.
     *
     * @param id The ID of the supplier to update.
     * @param supplierDetails The new details for the supplier.
     * @return The updated supplier.
     * @throws SupplierException If the supplier with the given ID is not found or update fails.
     */
    SupplierModel updateSupplier(String id, SupplierModel supplierDetails) throws SupplierException;

    /**
     * Deletes a supplier by its ID.
     * WARNING: Due to CascadeType.ALL, this will also delete all associated products.
     *
     * @param id The ID of the supplier to delete.
     * @throws SupplierException If the supplier with the given ID is not found.
     */
    void deleteSupplier(String id) throws SupplierException;
}
