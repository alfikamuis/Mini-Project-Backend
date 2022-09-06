package com.alfika.backendecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class Cart implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id",nullable = true)
    private int orderId;

    private String email;

    private String name;

    private int quantity;

    private double price;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn
    private Product productId;

    @ManyToOne
    @JoinColumn
    private User userId;
}
