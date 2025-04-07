package com.alember.my_warehouse.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

/**
 * Represents a category entity in the system.
 *
 * This class is mapped to the "category" table in the database and contains
 * details about the product category, including its name, description, and
 * the list of products associated with this category.
 *
 * Fields:
 * - id: Unique identifier for the category (auto-generated, hidden for serialization).
 * - name: Name of the category (non-nullable, unique).
 * - description: Description of the category (non-nullable, unique).
 *
 * Relationships:
 * - One-to-many relationship with ProductModel (a category can have multiple products).
 */
@Entity
@Data
@Table(name = "category")
public class CategoryModel {
	@Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

	@Column(nullable = false, unique = true)
    private String name;

	@Column(nullable = false, unique = true)
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductModel> products;
}
