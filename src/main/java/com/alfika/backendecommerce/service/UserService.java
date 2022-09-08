package com.alfika.backendecommerce.service;

import com.alfika.backendecommerce.model.Cart;
import com.alfika.backendecommerce.model.OrderItems;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.function.EntityResponse;

import java.security.Principal;
import java.util.List;

public interface UserService {

    List<Cart> viewCartUser (Principal user);
    List<Cart> updateQuantityInCart (Long id,int quantity,Principal user);
    ResponseEntity<?> addProductToCart (Long id, int quantity, Principal user);
    List<Cart> deleteProductInCart (Long id,Principal user);
    OrderItems orderApprovedByUser (Principal user);



}
