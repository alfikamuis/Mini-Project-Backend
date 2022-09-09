package com.alfika.backendecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id",nullable = true)
    private Long orderId;
    private String email;
    private String name;
    private int quantity;
    private double price;

    @Column(name = "status")
    private boolean status = true;

    // one to many
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "created_at")
    private Date createdAt;
}
