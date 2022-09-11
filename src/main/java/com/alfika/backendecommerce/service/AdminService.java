package com.alfika.backendecommerce.service;

import com.alfika.backendecommerce.model.OrderItems;
import com.alfika.backendecommerce.model.Product;
import com.alfika.backendecommerce.model.ViewOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    List<Product> findAllProduct();
    List<ViewOrder> viewAllOrderByUser();
    List<OrderItems> viewAllShippingOrderByUser();
    List<OrderItems> viewAllPendingOrderByUser();
    ResponseEntity<?> updateStatusOrder (Long id, String message);
    List<OrderItems> updateStatusPendingOrder (Long id,String message);
    Product addProduct(String name, String description, String stock, String price,
            MultipartFile image) throws IOException;

    Product updateProduct(Long id, String name, String description, String stock, String price,
            MultipartFile image) throws IOException;

    void deleteProduct(Long id);

}
