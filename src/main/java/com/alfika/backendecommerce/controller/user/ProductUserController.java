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
    private UserRepository userRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/get-product")
    public ResponseEntity<?> getAllProduct(){
        return ResponseEntity.ok(new ProductResponse(
                "Happy shopping",
                productRepository.findAll()));
    }


    @GetMapping("/order_items")
    public ResponseEntity<?> orderedItems(Principal currentUser){
        User user = getCurrentUser(currentUser);

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
            total=cart.getQuantity() * cart.getPrice();
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

    //get the user data via auth
    public User getCurrentUser(Principal currentUser){

        String username = currentUser.getName();
        User theUser =new User();

        if(theUser!=null){
            theUser=userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "Username <"+ username+"> Not Found!!")
                    );
        }
        return theUser;
    }
}
