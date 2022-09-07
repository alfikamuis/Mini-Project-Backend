package com.alfika.backendecommerce.service;

import com.alfika.backendecommerce.dto.ProductDTO;
import com.alfika.backendecommerce.model.Cart;
import com.alfika.backendecommerce.model.OrderItems;
import com.alfika.backendecommerce.model.Product;
import com.alfika.backendecommerce.model.User;
import com.alfika.backendecommerce.repository.CartRepository;
import com.alfika.backendecommerce.repository.OrderItemsRepository;
import com.alfika.backendecommerce.repository.ProductRepository;
import com.alfika.backendecommerce.repository.UserRepository;
import com.alfika.backendecommerce.response.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Override
    public List<Cart> viewCartUser(Principal currentUser) {
        User user = getCurrentUser(currentUser);
        return cartRepository.findByEmailAndIncart(user.getEmail(),true);
    }

    @Override
    public List<Cart> updateQuantityInCart(Long id, int quantity,Principal currentUser) {
        User user = getCurrentUser(currentUser);
        Cart cart = cartRepository.findByIdAndEmail(id, user.getEmail());
        cart.setPrice(cart.getPrice()); //update quantity should be change price?
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        return cartRepository.findByEmailAndIncart(user.getEmail(),true);
    }

    @Override
    public ResponseEntity<?> addProductToCart(Long id, int quantity, Principal currentUser) {
        User user = getCurrentUser(currentUser);
        Optional<Product> fetchProduct = productRepository.findById(id);
        Product product = fetchProduct.get();

        //check if found duplicate item in cart <optional>
        if(cartRepository.existsById(product.getId())){
            return ResponseEntity.ok(new CartResponse("please update the quantity"));
        }

        //check if the condition meets the quantity of product
        if(!productRepository.checkInventory(id,quantity)){
            return ResponseEntity.ok(new CartResponse("product not available within the quantity"));
        }

        //checking the product details
        Cart cart = new Cart();
        Date date = new Date();
        cart.setProductId(id);
        cart.setName(product.getName());
        cart.setPrice(product.getUnitPrice());
        cart.setQuantity(quantity);
        cart.setEmail(user.getEmail());
        cart.setCreatedAt(date);
        cartRepository.save(cart);

        return ResponseEntity.ok(new CartResponse(
                "the product "+ cart.getName()+" has been saved in your cart",
                cartRepository.findByEmailAndIncart(user.getEmail(),true)
        ));
    }

    @Override
    public List<Cart> deleteProductInCart(Long cartId, Principal currentUser) {
        User user = getCurrentUser(currentUser);
        Optional<Cart> fetchCart = cartRepository.findById(cartId);
        Cart cart = fetchCart.get();

        //return quantity to the db and delete it
        productRepository.addInventoryFromCart(cart.getProductId(), cart.getQuantity());
        cartRepository.deleteByIdAndEmail(cartId, user.getEmail());

        return cartRepository.findByEmailAndIncart(user.getEmail(),true);
    }

    @Override
    public void orderApprovedByUser(Principal currentUser) {
        User user = getCurrentUser(currentUser);

        //use email for future auth or send the order details to the email
        OrderItems orderItems = new OrderItems();
        orderItems.setEmail(user.getEmail());
        orderItems.setOrderStatus("pending");

        //save the order's date
        Date date = new Date();
        orderItems.setOrderDate(date);

        //sum total by findAll product in cart
        double total=0;
        List<Cart> carts = cartRepository.findByEmailAndIncart(user.getEmail(),true);
        for(Cart cart: carts){
            total += cart.getQuantity() * cart.getPrice();
            cart.setInCart(false);
        }
        orderItems.setTotalCost(total);

        //save to cart
        OrderItems theList = orderItemsRepository.save(orderItems);
        carts.forEach(items->{ items.setOrderId(theList.getId());
            cartRepository.save(items);
        });
    }

    //get the user data via auth
    public User getCurrentUser(Principal currentUser){

        String username = currentUser.getName();
        User theUser =new User();
        if(theUser!=null){
            theUser = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "Username <"+ username+"> Not Found!!")
                    );
        }
        return theUser;
    }
}
