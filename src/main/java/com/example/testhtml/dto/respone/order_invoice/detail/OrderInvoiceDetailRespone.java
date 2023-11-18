package com.example.testhtml.dto.respone.order_invoice.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInvoiceDetailRespone {
    private String productRomID;
    private String colorId;
    private String productName;
    private String quantityProduct;
    private String colorName;
    private String productRomName;
    private String priceProduce;
    private String note;
    private String status;
}
