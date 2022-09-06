package com.alfika.backendecommerce.controller;

import com.alfika.backendecommerce.model.ProductCategory;
import com.alfika.backendecommerce.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/product-category/{id}")
    public ProductCategory findById(@PathVariable ("id") Long id){
        return productCategoryService.findById(id);
    }

    /*
    @GetMapping("/product-category")
    public List<ProductCategory> findAll(){
        return productCategoryService.findAll();
    }
     */

    @PostMapping("/product-category")
    public ProductCategory create(@RequestBody ProductCategory productCategory){
        return productCategoryService.create(productCategory);
    }

    @PutMapping("/product-category")
    public ProductCategory update(@RequestBody ProductCategory productCategory){
        return productCategoryService.update(productCategory);
    }

    @DeleteMapping("/product-category/{id}")
    public void deleteById(@PathVariable ("id") Long id){
        productCategoryService.deleteById(id);
    }
}
