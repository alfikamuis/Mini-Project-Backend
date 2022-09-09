package com.alfika.backendecommerce.repository;

import com.alfika.backendecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//@RepositoryRestResource(collectionResourceRel = "product", path = "products")
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findById(Long id);
    void deleteById(Long id);

    //test------------------------------------------------------------------------------------------------
    @Query(value = "select unit_stock from products where id = (?1)",nativeQuery = true)
    Object checkInventory (Long id);
    @Query(value = "update products set unit_stock = unit_stock - (?2) where id = (?1)",nativeQuery = true)
    void reduceStockToInventory (Long id, int quantity);
    int findByUnitStock(Long id);
}

