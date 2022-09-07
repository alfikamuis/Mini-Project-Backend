package com.alfika.backendecommerce.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_category")
public class ProductCategory{

    //postpone due to buggy relation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@OneToMany
    //@JoinColumn(name = "category_name")
    @Column(name = "category_name")
    private String categoryName;

    //relation to table product
    //get /api/products
    //@OneToMany(
    //        fetch = FetchType.LAZY,
    //        cascade = CascadeType.ALL,
    //        mappedBy = "ca")
    //private Set<Product> product = new HashSet<>();

}
