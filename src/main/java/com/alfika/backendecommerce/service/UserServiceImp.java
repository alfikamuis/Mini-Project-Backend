package com.alfika.backendecommerce.service;

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
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
        return cartRepository.findByEmailAndStatusTrue(user.getEmail());
    }

    //--------------------------------------------------------------------update cart-------

    @Override
    public ResponseEntity<?> updateQuantityInCart(Long id, int quantity,Principal currentUser) {

        if(!cartRepository.findById(id).isPresent()){
            return ResponseEntity.badRequest().body(new CartResponse("cart by id not found!"));
        }

        User user = getCurrentUser(currentUser);
        Cart cart = cartRepository.findByIdAndEmail(id, user.getEmail());

        //change quantity
        Optional<Product> fetchProduct = productRepository.findById(cart.getProductId());
        Product product = fetchProduct.get();
        //check if the condition meets the quantity of product
        if( product.getUnitStock() - quantity < 0|| quantity > product.getUnitStock()){
            return ResponseEntity.badRequest().body(new CartResponse("stock product not available!"));
        }
        if(cart.getQuantity() < quantity){
            product.setUnitStock(product.getUnitStock() - (quantity- cart.getQuantity()));
        } else {
            product.setUnitStock(product.getUnitStock() + (cart.getQuantity()-quantity));
        }
        productRepository.save(product);

        cart.setPrice(cart.getPrice());
        cart.setQuantity(quantity);
        cartRepository.save(cart);

        return ResponseEntity.ok(new CartResponse("quantity changes ",
                cartRepository.findByEmailAndStatusTrue(user.getEmail())
        ));
    }

    //--------------------------------------------------------------------add cart-----------

    @Override
    public ResponseEntity<?> addProductToCart(Long id, int quantity, Principal currentUser) {

        if(!productRepository.findById(id).isPresent()){
            return ResponseEntity.badRequest().body(new CartResponse("product by id not found!"));
        }

        User user = getCurrentUser(currentUser);
        Optional<Product> fetchProduct = productRepository.findById(id);
        Product product = fetchProduct.get();

        //check if found duplicate item in cart <optional>
        if(cartRepository.existsByProductIdAndStatusTrue(id)){
            return ResponseEntity.badRequest().body(new CartResponse("duplicate item, please update quantity"));
        }

        //int inventory = productRepository.checkInventory(id,quantity);
        //check if the condition meets the quantity of product
        if( product.getUnitStock() - quantity < 0|| quantity > product.getUnitStock()){
            return ResponseEntity.badRequest().body(new CartResponse("stock product not available!"));
        }

        product.setUnitStock(product.getUnitStock() - quantity);
        productRepository.save(product);

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
                "the product "+ product.getName()+" has been saved in your cart",
                cartRepository.findByEmailAndStatusTrue(user.getEmail())
        ));
    }

    //--------------------------------------------------------------------delete cart-------
    @Override
    public ResponseEntity<?> deleteProductInCart(Long cartId, Principal currentUser) {

        if(!cartRepository.findById(cartId).isPresent()){
            return ResponseEntity.badRequest().body(new CartResponse("cart by id not found!"));
        }
        User user = getCurrentUser(currentUser);
        Optional<Cart> fetchCart = cartRepository.findById(cartId);
        Cart cart = fetchCart.get();

        //return quantity
        Optional<Product> fetchProduct = productRepository.findById(cart.getProductId());
        Product product = fetchProduct.get();
        product.setUnitStock(product.getUnitStock() + cart.getQuantity());
        productRepository.save(product);

        //return quantity to the db and delete it
        //productRepository.addInventoryFromCart(cart.getProductId(), cart.getQuantity());
        cartRepository.deleteByIdAndEmail(cartId, user.getEmail());

        return ResponseEntity.ok(new CartResponse("cart id:"+ cartId +" items deleted",
                cartRepository.findByEmailAndStatusTrue(user.getEmail())
        ));
    }


    //--------------------------------------------------------------------approve order-------
    @Override
    public OrderItems orderApprovedByUser(Principal currentUser) {
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
        List<Cart> carts = cartRepository.findByEmailAndStatusTrue(user.getEmail());
        if(carts.isEmpty()){
            return orderItems;
        }
        for(Cart cart: carts){
            total += cart.getQuantity() * cart.getPrice();
            cart.setStatus(false);
        }
        orderItems.setTotalCost(total);

        //save to order_items db
        OrderItems theList = orderItemsRepository.save(orderItems);
        carts.forEach(items->{ items.setOrderId(theList.getId());
            cartRepository.save(items);
        });
        return orderItems;
    }

    //get the user data via auth
    public User getCurrentUser(Principal currentUser){

        String username = currentUser.getName();
        User theUser = new User();
        if(theUser!=null){
            theUser = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "Username <"+ username+"> Not Found!!")
                    );
        }
        return theUser;
    }


    @Autowired
    private EntityManager entityManager;
    @Override
    public String addTest(Long id, int quantity, Principal user) {

        List result =entityManager.createNativeQuery("select checkinventory(?1,?2)",Product.class).getResultList();
                .setParameter(1,id)
                .setParameter(2,quantity)
                .getResultList().size();

        return user.getName() + result;
    }


}


