package com.alfika.backendecommerce.response;

import com.alfika.backendecommerce.model.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemsResponse {

    private String message;
    private List<OrderItems> placeOrderist;
    private OrderItems placeOrder;

    public OrderItemsResponse(String message, Optional<OrderItems> orderItems) {
    }
}
