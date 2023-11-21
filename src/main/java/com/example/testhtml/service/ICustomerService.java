package com.example.testhtml.service;

import com.example.testhtml.dto.request.Customer.CustomerRequest;
import com.example.testhtml.dto.respone.Customer.CustomerInfoResponse;
import com.example.testhtml.dto.respone.Customer.CustomerServiceResponse;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: POLY_DuyDV
 * @version: 1.0
 * @since: 2/27/2022
 * Project_name: Tech-cam
 */
public interface ICustomerService {
    List<CustomerInfoResponse> findCustomersByAll(String keyword);

}
