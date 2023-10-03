package com.example.testhtml.dto.respone.order_invoice.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author: hieu
 * @since: 29/06/2022
 * Project_name: com.example.testhtml.dto.respone.order_invoice.detail
 */
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
