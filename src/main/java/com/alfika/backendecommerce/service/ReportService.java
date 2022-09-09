package com.alfika.backendecommerce.service;

import com.alfika.backendecommerce.model.Cart;
import com.alfika.backendecommerce.model.OrderItems;

import java.util.List;

public interface ReportService {

    List<OrderItems> reportOrderItems ();
    List<Cart> reportCart ();
}
