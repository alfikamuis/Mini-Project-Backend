package com.alfika.backendecommerce.repository;

import com.alfika.backendecommerce.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems,Long> {

    Optional<OrderItems> findById(Long id);
    List<OrderItems> findByStatus (String message);
}
