package com.alfika.backendecommerce.controller.user;

import com.alfika.backendecommerce.model.Cart;
import com.alfika.backendecommerce.response.CartResponse;
import com.alfika.backendecommerce.response.OrderItemsResponse;
import com.alfika.backendecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
public class CartUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/view-cart")
    public ResponseEntity<?> viewCartUser(Principal currentUser){
        return ResponseEntity.ok(new CartResponse(
                "cart list", userService.viewCartUser(currentUser)
        ));
    }

    @GetMapping("/update-cart")
    public ResponseEntity<?> updateCart( @RequestParam("id") Long id, @RequestParam("quantity")int quantity,
            Principal currentUser){

        List<Cart> carts = userService.updateQuantityInCart(id,quantity,currentUser);
        return ResponseEntity.ok(new CartResponse("quantity and price changes ", carts
        ));
    }

    @GetMapping("/add-to-cart")
    public ResponseEntity<?> addProductToCart(@RequestParam("id") Long id, @RequestParam("quantity") int quantity,
            Principal currentUser) {

        return userService.addProductToCart(id,quantity,currentUser);
    }

    @DeleteMapping("/delete-cart")
    @Transactional
    public ResponseEntity<?> deleteCart( @RequestParam("cartId") Long id, Principal currentUser){

        return ResponseEntity.ok(new CartResponse("cart id:"+id+" items deleted",
                userService.deleteProductInCart(id,currentUser)
        ));
    }

    @GetMapping("/order_items")
    public ResponseEntity<?> orderedItems(Principal currentUser){
        userService.orderApprovedByUser(currentUser);
        return ResponseEntity.ok(new OrderItemsResponse(
                "Shipping out your order, check status regularly"));
    }
}
