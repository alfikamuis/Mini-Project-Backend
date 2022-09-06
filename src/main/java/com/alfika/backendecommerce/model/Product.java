package com.alfika.backendecommerce.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "products")
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @Column(name = "unit_stock")
    private int unitStock;

    @Column(name = "unit_price")
    private double unitPrice;

    @Lob
    @Column(name = "image_url")
    private byte[] imageUrl;

    //@ManyToOne
    //@JoinColumn (name = "category_id",nullable = false)
    //@JoinColumn(name = "category_id")
    //private String category;

    //date auto create
    @Basic(optional = false)
    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date(); // initialize created date

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date(); // initialize updated date

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt= new Date();
    }

}
