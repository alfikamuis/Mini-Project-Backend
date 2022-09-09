package com.alfika.backendecommerce.repository;

import com.alfika.backendecommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//@RepositoryRestResource(collectionResourceRel = "product", path = "products")
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findById(Long id);
    void deleteById(Long id);

    @Query(value = "select checkInventory(?1,?2)",nativeQuery = true)
    int checkInventory (Long id,Integer quantity);

    @Query(value = "select public.return_to_inventory(?1,?2)",nativeQuery = true)
    void addInventoryFromCart (Long id,Integer quantity);

    @Query(value = "select unit_stock from products p where p.id =?1",nativeQuery = true)
    int checkInventoryNative (Long id);

    @Query(value = "update products set unit_stock = ?2 where id = ?1",nativeQuery = true)
    void reduceInventoryNative (Long id,Integer quantity);

    int findByUnitStock(Long id);
}

