package com.alfika.backendecommerce.repository;

import com.alfika.backendecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRespository extends JpaRepository<Order,Long> {
}
