package com.alfika.backendecommerce.response;

import com.alfika.backendecommerce.model.ViewOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class ViewOrderResponse {

    private String message;
    private List<ViewOrder> viewOrderList = new ArrayList<>();
}
