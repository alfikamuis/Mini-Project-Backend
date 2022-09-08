package com.alfika.backendecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ViewOrder {

    //list order
    private Long orderId;
    private String orderBy;
    private String orderStatus;
    private double totalOrders;
    private List<Cart> products = new ArrayList<>();
}
