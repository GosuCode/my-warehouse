package com.alember.my_warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alember.my_warehouse.model.CategoryModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryModel, String>{


    Optional<CategoryModel> findById(String s);

    boolean existsById(String s);

    void deleteById(String s);

    @Transactional
    @Modifying
    @Query("update CategoryModel c set c.name = ?1, c.description = ?2 where c.id = ?3")
    void updateById(String name, String description, String id);
}
