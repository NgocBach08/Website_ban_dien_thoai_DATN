package com.example.testhtml.dto.request.product_property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPropertyRequest {
    private String romId;
    private String colorId;
    private String price;
    private String quantity;
    private String status;
}
