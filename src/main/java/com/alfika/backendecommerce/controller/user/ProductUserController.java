package com.alfika.backendecommerce.controller.user;

import com.alfika.backendecommerce.model.Product;
import com.alfika.backendecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
public class ProductUserController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/get-product")
    public Page<Product> getAllProduct(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "5") int size,
            @RequestParam (defaultValue = "id") String sortBy){

        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, sortBy);
        orders.add(order1);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(orders));
        return productRepository.findAll(pageRequest);
    }

}
