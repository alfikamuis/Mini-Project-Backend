package com.alfika.backendecommerce.service;

import com.alfika.backendecommerce.model.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductCategoryService {

    ProductCategory findById(Long id);
    List<ProductCategory> findAll();
    ProductCategory create(ProductCategory productCategory);
    ProductCategory update(ProductCategory productCategory);
    void deleteById(Long id);
}
