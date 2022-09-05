package com.alfika.backendecommerce.repository;

import com.alfika.backendecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRespository extends JpaRepository<Customer,Long> {
}
