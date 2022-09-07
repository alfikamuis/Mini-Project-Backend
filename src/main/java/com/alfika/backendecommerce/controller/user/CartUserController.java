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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
public class CartUserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private ProductUserController getuser = new ProductUserController();

    @GetMapping("/view-cart")
    public ResponseEntity<?> getCart(Principal currentUser){
        User user = getuser.getCurrentUser(currentUser);
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
        cart.setPrice(cart.getPrice());
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

        //check if found duplicate item in cart <optional>
        Optional<Product> product=productRepository.findById(id);
        if(cartRepository.existsById(product.get().getId())){
            return ResponseEntity.ok(new CartResponse(
                    "product already in your cart, please update the quantity")
            );
        }

        //check if meets the quantity in the inventory
        if(!productRepository.checkInventory(id,quantity)){
            return ResponseEntity.ok(new CartResponse(
                    "product not available within the quantity")
            );
        }

        //checking the product details
        Cart cart = new Cart();
        Date date=new Date();
        cart.setProductId(id);
        cart.setName(product.get().getName());
        cart.setPrice(product.get().getUnitPrice());
        cart.setQuantity(quantity);
        cart.setEmail(user.getEmail());
        cart.setCreatedAt(date);
        cartRepository.save(cart);
        return ResponseEntity.ok(new CartResponse(
                "the product "+ cart.getName()+" has been saved in your cart",
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
                "cart id:"+id+" items deleted",
                cartRepository.findByEmail(user.getEmail())
        ));

    }

    @GetMapping("/order_items")
    public ResponseEntity<?> orderedItems(Principal currentUser){
        User user = getuser.getCurrentUser(currentUser);

        //use email for future auth or send the order to the email
        OrderItems orderItems = new OrderItems();
        orderItems.setEmail(user.getEmail());
        orderItems.setOrderStatus("pending");

        //save the order's date
        Date date=new Date();
        orderItems.setOrderDate(date);

        //sum total
        double total=0;
        List<Cart> carts = cartRepository.findAllByEmail(user.getEmail());
        for(Cart cart: carts){
            total += cart.getQuantity() * cart.getPrice();
        }
        orderItems.setTotalCost(total);

        //save to cart
        OrderItems thelist = orderItemsRepository.save(orderItems);
        carts.forEach(items->{
            items.setOrderId(thelist.getId());
            cartRepository.save(items);
        });

        return ResponseEntity.ok(new OrderItemsResponse(
                "Shipping out your order, please wait for admin evaluations"));
    }
}
