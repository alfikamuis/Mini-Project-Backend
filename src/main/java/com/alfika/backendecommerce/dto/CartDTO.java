package com.alfika.backendecommerce.dto;

import javax.persistence.Column;
import java.util.Date;

public class CartDTO {
    private Long id;
    private Long productId;
    private String name;
    private int quantity;
    private double price;

    public CartDTO(Long id, Long productId, String name, int quantity, double price) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}
