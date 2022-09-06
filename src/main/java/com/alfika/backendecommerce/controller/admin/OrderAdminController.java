package com.alfika.backendecommerce.controller.admin;

import com.alfika.backendecommerce.model.OrderItems;
import com.alfika.backendecommerce.model.ViewOrder;
import com.alfika.backendecommerce.repository.CartRepository;
import com.alfika.backendecommerce.repository.OrderItemsRepository;
import com.alfika.backendecommerce.response.OrderItemsResponse;
import com.alfika.backendecommerce.response.ViewOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class OrderAdminController {

    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/view-orders")
    public ResponseEntity<?> getAllOrders(){
        List<ViewOrder> viewOrdersArr = new ArrayList<>();
        List<OrderItems> orderItems = orderItemsRepository.findAll(); //fetch data from order_items db

        orderItems.forEach((items) ->{
            ViewOrder toViewOrder = new ViewOrder();
            toViewOrder.setOrderId(items.getId());
            toViewOrder.setOrderBy(items.getEmail());
            toViewOrder.setOrderStatus(items.getOrderStatus());
            toViewOrder.setProducts(cartRepository.findAllById(items.getId()));
            viewOrdersArr.add(toViewOrder);
        });
        return ResponseEntity.ok(new ViewOrderResponse(
                "All list Order", viewOrdersArr
        ));
    }

    @PostMapping("/update-order")
    public ResponseEntity<?> updateOrder(
            @RequestParam(name="id") Long id,
            @RequestParam(name="status") String status)
    {
        Optional<OrderItems> orderItems = orderItemsRepository.findById(id);
        orderItems.get().setOrderStatus(status);
        orderItemsRepository.save(orderItems.get());
        return ResponseEntity.ok(new OrderItemsResponse(
                "status order id: "+ id +" has been updated", orderItems));
    }


}
