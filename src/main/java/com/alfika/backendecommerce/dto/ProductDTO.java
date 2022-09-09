package com.alfika.backendecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class ProductDTO {
    private Long id;
    private String description;
    private String productname;
    private String price;
    private String quantity;
    private MultipartFile productimage;

    public ProductDTO(Long id, String description, String productname, String price, String quantity, MultipartFile productimage) {
        this.id = id;
        this.description = description;
        this.productname = productname;
        this.price = price;
        this.quantity = quantity;
        this.productimage = productimage;
    }
}
