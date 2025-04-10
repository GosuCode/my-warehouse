package com.alember.my_warehouse.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.alember.my_warehouse.model.SupplierModel;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierModel, String>{

}
