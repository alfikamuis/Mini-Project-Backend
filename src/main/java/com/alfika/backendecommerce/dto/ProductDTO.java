package com.alfika.backendecommerce.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductDTO {
    private Long id;
    private String description;
    private String productname;
    private String price;
    private String quantity;
    private MultipartFile productimage;
}
