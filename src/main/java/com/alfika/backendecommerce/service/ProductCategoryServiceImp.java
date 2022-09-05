package com.alfika.backendecommerce.service;

import com.alfika.backendecommerce.exception.ResourceNotFoundException;
import com.alfika.backendecommerce.model.ProductCategory;
import com.alfika.backendecommerce.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImp implements ProductCategoryService{

    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    @Override
    public ProductCategory findById(Long id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product id:"+ id +"not found!"));
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory create(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategory update(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public void deleteById(Long id) {
        productCategoryRepository.deleteById(id);
    }
}
