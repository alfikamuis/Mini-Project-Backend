package com.alfika.backendecommerce.service.admin;

import com.alfika.backendecommerce.model.Product;
import com.alfika.backendecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductAdminServiceImp implements ProductAdminService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(String name, String description, String stock, String price, MultipartFile image) throws IOException {
        Product product = new Product();

        product.setName(name);
        product.setDescription(description);
        product.setUnitStock(Integer.parseInt(stock));
        product.setUnitPrice(Double.parseDouble(price));
        product.setImageUrl(image.getBytes());
        productRepository.save(product);

        return product;
    }

    @Override
    public Product updateProduct(Long id,String name, String description, String stock, String price, MultipartFile image) throws IOException {
        Optional<Product> product1 = productRepository.findById(id);
        Optional<Product> productOriginal = productRepository.findById(id);
        if(!image.isEmpty()){
            product1.get().setName(name);
            product1.get().setDescription(description);
            product1.get().setUnitPrice(Double.parseDouble(price));
            product1.get().setUnitStock(Integer.parseInt(stock));
            product1.get().setImageUrl(image.getBytes());
        }
        else{
            product1.get().setName(name);
            product1.get().setDescription(description);
            product1.get().setUnitPrice(Double.parseDouble(price));
            product1.get().setUnitStock(Integer.parseInt(stock));
            product1.get().setImageUrl(productOriginal.get().getImageUrl());
        }

        return productRepository.save(product1.get());
    }
}
