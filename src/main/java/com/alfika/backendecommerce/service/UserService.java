package com.alfika.backendecommerce.service;

import com.alfika.backendecommerce.model.Cart;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface UserService {

    List<Cart> viewCartUser (Principal user);
    List<Cart> updateQuantityInCart (Long id,int quantity,Principal user);
    ResponseEntity<?> addProductToCart (Long id, int quantity, Principal user);
    List<Cart> deleteProductInCart (Long id,Principal user);

    void orderApprovedByUser (Principal user);

}
