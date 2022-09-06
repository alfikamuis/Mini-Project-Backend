package com.alfika.backendecommerce.repository;

import com.alfika.backendecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    List<Cart> findAllById(Long Id);
    List<Cart> findByEmail(String email);
    boolean existsById(Long productId);
    Cart findById(int cartId);
    Cart findByIdAndEmail(int cartId,String email);
    void deleteByIdAndEmail(int cartId,String email);
    List<Cart> findAllByEmail(String email);

}
