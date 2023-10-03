package com.example.testhtml.service;

import com.example.testhtml.dto.request.order.OrderRequest;
import com.example.testhtml.dto.respone.order.OrderRespone;

import java.text.ParseException;
import java.util.List;

/**
 * Description:
 *
 * @author: hieu
 * @since: 22/08/2022
 * Project_name: com.example.testhtml.service
 */
public interface IOrderService {
    List<OrderRespone> findAllOrder();

    OrderRespone findByOrder(String id);

    String addOrder(OrderRequest request);

    String updateOrder(OrderRequest request);

    String confirmOrder(OrderRequest request) throws ParseException;

    String shippingOrder(OrderRequest request);

    String exportOrder(OrderRequest request);

    String doneOrder(String id);

    String deleteOrder(String id);




}
