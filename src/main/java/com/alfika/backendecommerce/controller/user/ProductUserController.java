package com.alfika.backendecommerce.controller.user;

import com.alfika.backendecommerce.model.Cart;
import com.alfika.backendecommerce.model.OrderItems;
import com.alfika.backendecommerce.model.Product;
import com.alfika.backendecommerce.model.User;
import com.alfika.backendecommerce.repository.CartRepository;
import com.alfika.backendecommerce.repository.OrderItemsRepository;
import com.alfika.backendecommerce.repository.ProductRepository;
import com.alfika.backendecommerce.repository.UserRepository;
import com.alfika.backendecommerce.response.CartResponse;
import com.alfika.backendecommerce.response.OrderItemsResponse;
import com.alfika.backendecommerce.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
public class ProductUserController {

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/get-product")
    public Page<Product> getAllProduct(@RequestParam int page, @RequestParam int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findAll(pageRequest);
    }

}
