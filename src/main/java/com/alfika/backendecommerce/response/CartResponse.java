package com.alfika.backendecommerce.response;

import com.alfika.backendecommerce.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CartResponse {
    private String message;
    private List<Cart> carts;

    public CartResponse(String message) {
    }
}
