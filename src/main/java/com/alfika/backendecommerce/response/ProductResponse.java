package com.alfika.backendecommerce.response;

import com.alfika.backendecommerce.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ProductResponse {
    private String message;
    private List<Product> productList;
    private Product product;

    public ProductResponse(String message, List<Product> productList) {
        this.message = message;
        this.productList = productList;
    }

    public ProductResponse(String message, Product product) {
        this.message = message;
        this.product = product;
    }

}
