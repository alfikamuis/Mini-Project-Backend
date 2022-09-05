package com.alfika.backendecommerce.repository;

import com.alfika.backendecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRespository extends JpaRepository<OrderItem,Long> {
}
