package com.alfika.backendecommerce.controller.admin;

import com.alfika.backendecommerce.dto.ProductDTO;
import com.alfika.backendecommerce.model.Product;
import com.alfika.backendecommerce.repository.ProductRepository;
import com.alfika.backendecommerce.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/api/admin")
//@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class ProductAdminController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/get-product")
    public ResponseEntity<?> getProducts(){
        return ResponseEntity.ok(new ProductResponse(
                "Products find",
                productRepository.findAll()
        ));
    }

    @PostMapping("/add-product")
    public ResponseEntity<?> addProduct(
            @RequestParam(name="name") String name,
            @RequestParam(name="description") String description,
            @RequestParam(name="stock") String stock,
            @RequestParam(name="price") String price,
            @RequestParam(name="file") MultipartFile imageUrl
            //@RequestParam(name="category") String category
    ) throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setUnitStock(Integer.parseInt(stock));
        product.setUnitPrice(Double.parseDouble(price));
        product.setImageUrl(imageUrl.getBytes());
        //product.setCategory(category);
        productRepository.save(product);

        ProductDTO productDTO = new ProductDTO();
        return ResponseEntity.ok(new ProductResponse(
                "Product created.",product
        ));
    }

    @PutMapping("/update-product")
    public ResponseEntity<?> updateProduct(
            @RequestParam(name="id") Long id,
            @RequestParam(name="name") String name,
            @RequestParam(name="description") String description,
            @RequestParam(name="stock") String stock,
            @RequestParam(name="price") String price,
            @RequestParam(name="file",required = false) MultipartFile imageUrl
            //@RequestParam(name="category") String category
    ) throws IOException{

        Optional<Product> product1 = productRepository.findById(id);
        Optional<Product> productOriginal = productRepository.findById(id);
        if(!imageUrl.isEmpty()){
            product1.get().setName(name);
            product1.get().setDescription(description);
            product1.get().setUnitPrice(Double.parseDouble(price));
            product1.get().setUnitStock(Integer.parseInt(stock));
            product1.get().setImageUrl(imageUrl.getBytes());
        }
        else{
            product1.get().setName(name);
            product1.get().setDescription(description);
            product1.get().setUnitPrice(Double.parseDouble(price));
            product1.get().setUnitStock(Integer.parseInt(stock));
            product1.get().setImageUrl(productOriginal.get().getImageUrl());

        }
        productRepository.save(product1.get());
        return ResponseEntity.ok(new ProductResponse(
                "update product id:" + id, product1.get()));
    }

    @DeleteMapping("/delete-product")
    @Transactional
    public ResponseEntity<?> deleteProduct(@RequestParam (name ="id") Long id){
        productRepository.deleteById(id);
        return ResponseEntity.ok(new ProductResponse(
                "Delete Product id:"+ id,
                productRepository.findAll()
        ));
    }

}
