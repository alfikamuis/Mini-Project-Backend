package com.alfika.backendecommerce.service;

import com.alfika.backendecommerce.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Product findById(Long id);
    List<Product> findAll();
    Product create(Product product);
    Product update(Product product);
    Product updateImage(Long id,String imageUrl);
    void deleteById(Long id);
}
