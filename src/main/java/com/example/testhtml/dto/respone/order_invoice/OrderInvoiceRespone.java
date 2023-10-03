package com.example.testhtml.dto.respone.order_invoice;

import com.example.testhtml.dto.respone.order_invoice.detail.OrderInvoiceDetailRespone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description:
 *
 * @author: hieu
 * @since: 29/06/2022
 * Project_name: com.example.testhtml.dto.respone.order_invoice
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInvoiceRespone {

    private Long id;
    private String note;
    private String codeOrder;
    private Integer status;
    private String receiveDate;
    private String createDate;
    private String supplierName;
    private String supplierId;
    private String totalMonenyString;
    private String tienThuaString;
    private String giamGiaString;
    private String phaiTraNCCString;
    private String staffName;
    private long totalMoneny;
    private long tienThua;
    private long giamGia;
    private long phaiTraNCC;
    private List<OrderInvoiceDetailRespone> orderDetail;

}
