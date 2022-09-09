package com.alfika.backendecommerce.service;

import com.alfika.backendecommerce.model.Cart;
import com.alfika.backendecommerce.model.OrderItems;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface UserService {

    List<Cart> viewCartUser (Principal user);
    ResponseEntity<?> updateQuantityInCart (Long id,int quantity,Principal user);
    ResponseEntity<?> addProductToCart (Long id, int quantity, Principal user);
    ResponseEntity<?> deleteProductInCart (Long id,Principal user);
    OrderItems orderApprovedByUser (Principal user);

    //-------------------------------------------------------------test
    Object addTest (Long id, int quantity, Principal user);

}
