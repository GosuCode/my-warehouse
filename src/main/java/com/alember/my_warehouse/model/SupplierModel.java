package com.alember.my_warehouse.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a supplier entity in the system.
 *
 * This class is mapped to the "supplier" table in the database and contains
 * details about the supplier, including their name, phone, email, address,
 * and the products they supply.
 *
 * Fields:
 * - id: Unique identifier for the supplier.
 * - name: Name of the supplier (non-nullable).
 * - phone: Phone number of the supplier (non-nullable).
 * - email: Email address of the supplier (non-nullable).
 * - address: Address of the supplier (non-nullable).
 *
 * Relationships:
 * - One-to-many relationship with ProductModel (a supplier can provide multiple products).
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supplier")
public class SupplierModel {

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ProductModel> suppliedProducts;
}
