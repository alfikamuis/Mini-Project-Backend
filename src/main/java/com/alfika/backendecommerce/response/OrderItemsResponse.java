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
    private List<OrderItems> orderItemsList;
    private OrderItems orderItems;

    public OrderItemsResponse(String message, OrderItems orderItems) {
        this.message = message;
        this.orderItems = orderItems;
    }

    public OrderItemsResponse(String message, List<OrderItems> orderItemsList) {
        this.message = message;
        this.orderItemsList = orderItemsList;
    }
    public OrderItemsResponse(String message) {
        this.message = message;
    }
}
