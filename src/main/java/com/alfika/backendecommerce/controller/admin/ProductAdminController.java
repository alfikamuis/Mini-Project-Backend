package com.alfika.backendecommerce.controller.admin;

import com.alfika.backendecommerce.model.Product;
import com.alfika.backendecommerce.response.ProductResponse;
import com.alfika.backendecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/admin")
//@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class ProductAdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/get-product")
    public ResponseEntity<?> getProducts(){
        return ResponseEntity.ok(new ProductResponse(
                "Products find",
                adminService.findAllProduct()
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

        Product product = adminService.addProduct(name,description,stock,price,imageUrl);
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

        Product product = adminService.updateProduct(id,name,description,stock,price,imageUrl );
        return ResponseEntity.ok(new ProductResponse(
                "update product id:" + id, product));
    }

    @DeleteMapping("/delete-product")
    @Transactional
    public ResponseEntity<?> deleteProduct(@RequestParam (name ="id") Long id){
        adminService.deleteProduct(id);
        return ResponseEntity.ok(new ProductResponse(
                "Delete Product id:"+ id, adminService.findAllProduct()
        ));
    }

}
