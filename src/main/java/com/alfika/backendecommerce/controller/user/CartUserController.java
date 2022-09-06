package com.alfika.backendecommerce.controller.user;

import com.alfika.backendecommerce.model.Cart;
import com.alfika.backendecommerce.model.Product;
import com.alfika.backendecommerce.model.User;
import com.alfika.backendecommerce.repository.CartRepository;
import com.alfika.backendecommerce.repository.ProductRepository;
import com.alfika.backendecommerce.response.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
public class CartUserController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    ProductUserController getuser = new ProductUserController();

    @GetMapping("/view-cart")
    public ResponseEntity<?> getCart(Principal currentUser){
        User user=getuser.getCurrentUser(currentUser);
        return ResponseEntity.ok(new CartResponse(
                "cart list",
                cartRepository.findByEmail(user.getEmail())
        ));
    }

    @GetMapping("/update-cart")
    public ResponseEntity<?> updateCart(
            @RequestParam("id") Long id,
            @RequestParam("quantity")int quantity,
            Principal currentUser){

        User user = getuser.getCurrentUser(currentUser);
        Cart cart=cartRepository.findByIdAndEmail(id, user.getEmail());
        cart.setPrice(cart.getPrice() * quantity);
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        return ResponseEntity.ok(new CartResponse(
                "quantity and price changes ",cartRepository.findByEmail(user.getEmail())
        ));
    }

    @GetMapping("/add-to-cart")
    public ResponseEntity<?> addProductToCart(
            @RequestParam("id") Long id,
            @RequestParam("quantity") int quantity,
            Principal currentUser) {

        User user = getuser.getCurrentUser(currentUser);

        //check if there us diplicate item in cart <optional>
        Optional<Product> product=productRepository.findById(id);
        if(cartRepository.existsById(product.get().getId())){
            return ResponseEntity.ok(new CartResponse(
                    "product already in your cart, please update the quantity")
            );
        }

        //checking the product details
        Cart cart = new Cart();
        Date date=new Date();
        cart.setProductId(id);
        cart.setName(product.get().getName());
        cart.setPrice(product.get().getUnitPrice() * quantity);
        cart.setQuantity(quantity);
        cart.setEmail(user.getEmail());
        cart.setCreatedAt(date);
        cartRepository.save(cart);
        return ResponseEntity.ok(new CartResponse(
                "the product has been saved in your cart",
                cartRepository.findByEmail(user.getEmail())
        ));
    }

    @DeleteMapping("/delete-cart")
    @Transactional
    public ResponseEntity<?> deleteCart(
            @RequestParam("cartId") Long id,
            Principal currentUser){

        User user=getuser.getCurrentUser(currentUser);
        cartRepository.deleteByIdAndEmail(id, user.getEmail());
        return ResponseEntity.ok(new CartResponse(
                "cart items deleted",
                cartRepository.findByEmail(user.getEmail())
        ));

    }
}
