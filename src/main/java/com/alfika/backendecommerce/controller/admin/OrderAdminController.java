package com.alfika.backendecommerce.controller.admin;


import com.alfika.backendecommerce.data.ExporterOrderItems;
import com.alfika.backendecommerce.model.OrderItems;
import com.alfika.backendecommerce.model.ViewOrder;
import com.alfika.backendecommerce.response.OrderItemsResponse;
import com.alfika.backendecommerce.response.ViewOrderResponse;
import com.alfika.backendecommerce.service.AdminService;
import com.alfika.backendecommerce.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class OrderAdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/view-orders")
    public ResponseEntity<?> getAllOrders(){
        List<ViewOrder> viewOrder = adminService.viewAllOrderByUser();
        return ResponseEntity.ok(new ViewOrderResponse(
                "All list Order", viewOrder
        ));
    }

    @GetMapping("/view-status-order")
    public ResponseEntity<?> viewPendingOrder(
            @RequestParam(name="status",defaultValue = "pending") String status)
    {
        List<OrderItems> orderItems;
        if (status.equalsIgnoreCase("pending"))
        {
            orderItems = adminService.viewAllPendingOrderByUser();
            return ResponseEntity.ok(new OrderItemsResponse(
                    "list of status pending orders", orderItems));
        }
        orderItems = adminService.viewAllShippingOrderByUser();
        return ResponseEntity.ok(new OrderItemsResponse(
                "list if status shipping orders", orderItems));
    }

    @PostMapping("/update-order")
    public ResponseEntity<?> updateOrder(
            @RequestParam(name="id") Long id,
            @RequestParam(name="status") String status)
    {
        return adminService.updateStatusOrder(id,status);
    }

    @GetMapping("/report/order")
    public void exportOrderToExcel(HttpServletResponse response, Principal principal) throws IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String header = "Content-Disposition";
        String headerValue = "attachment; filename="+ principal.getName() +"_"+currentDateTime + ".xlsx";

        List<OrderItems> orderItemsList = reportService.reportOrderItems();

        ExporterOrderItems exporterOrderItems = new ExporterOrderItems(orderItemsList);
        exporterOrderItems.exports(response);

    }


}
