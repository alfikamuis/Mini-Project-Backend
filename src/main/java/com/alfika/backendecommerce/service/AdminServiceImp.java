package com.alfika.backendecommerce.service;

import com.alfika.backendecommerce.model.OrderItems;
import com.alfika.backendecommerce.model.Product;
import com.alfika.backendecommerce.model.ViewOrder;
import com.alfika.backendecommerce.repository.CartRepository;
import com.alfika.backendecommerce.repository.OrderItemsRepository;
import com.alfika.backendecommerce.repository.ProductRepository;
import com.alfika.backendecommerce.response.CartResponse;
import com.alfika.backendecommerce.response.OrderItemsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<ViewOrder> viewAllOrderByUser() {
        List<ViewOrder> viewOrdersArr = new ArrayList<>();
        List<OrderItems> orderItems = orderItemsRepository.findAll(); //fetch data from order_items db

        orderItems.forEach((items) ->{
            ViewOrder toViewOrder = new ViewOrder();
            toViewOrder.setOrderId(items.getId());
            toViewOrder.setOrderBy(items.getEmail());
            toViewOrder.setOrderStatus(items.getOrderStatus());
            toViewOrder.setTotalOrders(items.getTotalCost());
            toViewOrder.setProducts(cartRepository.findByOrderId(items.getId()));
            viewOrdersArr.add(toViewOrder);
        });
        return viewOrdersArr;
    }

    @Override
    public List<OrderItems> viewAllPendingOrderByUser() {
        return orderItemsRepository.findByOrderStatus("pending");
    }
    @Override
    public List<OrderItems> viewAllShippingOrderByUser() {
        return orderItemsRepository.findByOrderStatus("shipping");
    }

    @Override
    public ResponseEntity<?> updateStatusOrder(Long id,String status) {

        if (!orderItemsRepository.existsById(id)){
            return ResponseEntity.badRequest().body(new OrderItemsResponse("order id not found"));
        }
        Optional<OrderItems> orderItems = orderItemsRepository.findById(id);
        OrderItems items = orderItems.get();
        items.setOrderStatus(status);
        orderItemsRepository.save(items);

        return ResponseEntity.ok(new OrderItemsResponse(
                "status order id: "+ id +" has been updated", items));
    }

    @Override
    public List<OrderItems> updateStatusPendingOrder(Long id, String message) {
        List<OrderItems> orderItems = orderItemsRepository.findByOrderStatus("pending");
        return orderItems;
    }

    @Override
    public Product addProduct(String name, String description, String stock, String price, MultipartFile image) throws IOException {
        Product product = new Product();

        product.setName(name);
        product.setDescription(description);
        product.setUnitStock(Integer.parseInt(stock));
        product.setUnitPrice(Double.parseDouble(price));
        product.setImageUrl(image.getBytes());
        productRepository.save(product);

        return product;
    }

    @Override
    public Product updateProduct(Long id,String name, String description, String stock, String price, MultipartFile image) throws IOException {
        Optional<Product> product1 = productRepository.findById(id);
        Optional<Product> productOriginal = productRepository.findById(id);
        if(!image.isEmpty()){
            product1.get().setName(name);
            product1.get().setDescription(description);
            product1.get().setUnitPrice(Double.parseDouble(price));
            product1.get().setUnitStock(Integer.parseInt(stock));
            product1.get().setImageUrl(image.getBytes());
        }
        else{
            product1.get().setName(name);
            product1.get().setDescription(description);
            product1.get().setUnitPrice(Double.parseDouble(price));
            product1.get().setUnitStock(Integer.parseInt(stock));
            product1.get().setImageUrl(productOriginal.get().getImageUrl());
        }

        return productRepository.save(product1.get());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
