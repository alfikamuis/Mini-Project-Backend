package com.alfika.backendecommerce.controller.admin;

import com.alfika.backendecommerce.model.OrderItems;
import com.alfika.backendecommerce.model.ViewOrder;
import com.alfika.backendecommerce.response.OrderItemsResponse;
import com.alfika.backendecommerce.response.ViewOrderResponse;
import com.alfika.backendecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class OrderAdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/view-orders")
    public ResponseEntity<?> getAllOrders(){
        List<ViewOrder> viewOrder = adminService.viewAllOrderByUser();
        return ResponseEntity.ok(new ViewOrderResponse(
                "All list Order", viewOrder
        ));
    }

    @GetMapping("/view-pending-order")
    public ResponseEntity<?> updatePendingOrder(
            @RequestParam(name="id") Long id,
            @RequestParam(name="status") String status)
    {
        List<OrderItems> orderItems = adminService.updateStatusPendingOrder(id,status);
        return ResponseEntity.ok(new OrderItemsResponse(
                "status order id: "+ id +" has been updated", orderItems));
    }

    @PostMapping("/update-order")
    public ResponseEntity<?> updateOrder(
            @RequestParam(name="id") Long id,
            @RequestParam(name="status") String status)
    {
        OrderItems orderItems = adminService.updateStatusOrder(id,status);
        return ResponseEntity.ok(new OrderItemsResponse(
                "status order id: "+ id +" has been updated", orderItems));
    }


}
