package com.alember.my_warehouse.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a product entity in the system.
 *
 * This class is mapped to the "product" table in the database and contains
 * details about the product, including its SKU, name, description, price,
 * quantity, category, and supplier.
 *
 * Fields:
 * - id: Unique identifier for the product.
 * - sku: Stock Keeping Unit, unique identifier for each product (non-nullable).
 * - name: Name of the product (non-nullable).
 * - description: Description of the product (optional, up to 1000 characters).
 * - price: Price of the product (non-nullable).
 * - quantity: Available quantity of the product (non-nullable).
 * - category: The category to which the product belongs (non-nullable).
 * - supplier: The supplier providing the product (optional).
 *
 * Relationships:
 * - Many-to-one relationship with CategoryModel (each product belongs to one category).
 * - Many-to-one relationship with SupplierModel (each product may have a supplier).
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class ProductModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private CategoryModel category;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @JsonBackReference
    private SupplierModel supplier;
	
}
