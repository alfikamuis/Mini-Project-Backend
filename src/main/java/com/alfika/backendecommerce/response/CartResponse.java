package com.alfika.backendecommerce.response;

import com.alfika.backendecommerce.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class CartResponse {
    private String message;
    private List<Cart> carts;

    public CartResponse(String message) {
        this.message = message;
    }
    public CartResponse(String message,List<Cart> carts) {
        this.message = message;
        this.carts = carts;
    }
}
