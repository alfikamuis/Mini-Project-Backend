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
@Table(name = "order")
public class Order implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn (name = "customer_id")
    private Customer customerId;

    @Column (name = "sum")
    private BigDecimal sum;

    @Column (name = "total")
    private BigDecimal total;

    @Temporal(TemporalType.DATE)
    @Column (name = "date_order")
    private Date dateOrder;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
}
