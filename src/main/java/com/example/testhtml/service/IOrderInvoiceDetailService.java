package com.example.testhtml.service;

import com.example.testhtml.dto.request.orderinvoicedetail.OrderInvoiceDetailRequest;
import com.example.testhtml.dto.respone.order_invoice.detail.OrderInvoiceDetailRespone;
import com.example.testhtml.entity.InvoiceOrderEntity;

import java.util.List;

public interface IOrderInvoiceDetailService {

    String createOrderInvoiceDetail(List<OrderInvoiceDetailRequest> list, InvoiceOrderEntity entity);

    String updateOrderInvoiceDetail(List<OrderInvoiceDetailRequest> list, InvoiceOrderEntity entity);

    List<OrderInvoiceDetailRespone> findByOrderInvoice(Long id);

    String createNhapDetail(List<OrderInvoiceDetailRequest> list, InvoiceOrderEntity entity);
    String updateNhapDetail(List<OrderInvoiceDetailRequest> list, InvoiceOrderEntity entity);
}
