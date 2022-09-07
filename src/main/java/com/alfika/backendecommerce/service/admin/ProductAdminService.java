package com.alfika.backendecommerce.service.admin;

import com.alfika.backendecommerce.dto.ProductDTO;
import com.alfika.backendecommerce.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductAdminService {

    List<Product> findAllProduct();
    Product addProduct(
            String name,
            String description,
            String stock,
            String price,
            MultipartFile image) throws IOException;

    Product updateProduct(
            Long id,
            String name,
            String description,
            String stock,
            String price,
            MultipartFile image) throws IOException;
}
