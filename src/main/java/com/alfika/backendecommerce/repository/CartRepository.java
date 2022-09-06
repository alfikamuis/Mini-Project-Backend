package com.alfika.backendecommerce.repository;

import com.alfika.backendecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    List<Cart> findAllById(Long Id);
    List<Cart> findByEmail(String email);
    boolean existsById(Long productId);
    Optional<Cart> findById(Long id);
    Cart findByIdAndEmail(Long id,String email);
    void deleteByIdAndEmail(Long id,String email);
    List<Cart> findAllByEmail(String email);

}
