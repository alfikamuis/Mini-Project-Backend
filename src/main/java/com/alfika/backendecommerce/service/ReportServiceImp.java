package com.alfika.backendecommerce.service;

import com.alfika.backendecommerce.model.Cart;
import com.alfika.backendecommerce.model.OrderItems;
import com.alfika.backendecommerce.repository.OrderItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class ReportServiceImp implements ReportService{

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Override
    public List<OrderItems> reportOrderItems() {
        return orderItemsRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public List<Cart> reportCart() {
        return null;
    }
}
