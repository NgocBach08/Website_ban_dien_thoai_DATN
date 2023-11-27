package com.example.testhtml.api.impl;

import com.example.testhtml.api.CustomerApi;
import com.example.testhtml.api.CustomerApi;
import com.example.testhtml.constant.ConstansErrorCode;
import com.example.testhtml.dto.request.Customer.CustomerRequest;
import com.example.testhtml.dto.respone.Customer.CustomerInfoResponse;
import com.example.testhtml.dto.respone.Customer.CustomerResponse;
import com.example.testhtml.dto.respone.Customer.CustomerServiceResponse;
import com.example.testhtml.exception.WorldPhoneExp;
import com.example.testhtml.service.ICustomerService;
import com.example.testhtml.type.CommonTypeMethod;
import com.example.testhtml.type.CustomerStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Description:
 *
 * @version: 1.0
 * @since: 1/23/2022
 * Project_name: GMO_QuanLyTaiSan
 */

@RestController
public class CustomerApiImpl implements CustomerApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerApiImpl.class);
    @Autowired
    private ICustomerService customerService;


    @Override
    public List<CustomerInfoResponse> findCustomersByAll(String keyWord) {
        List<CustomerInfoResponse> customerInfoResponses;
        if (keyWord == null || keyWord.isEmpty()) {
            return null;
        }
        customerInfoResponses = customerService.findCustomersByAll(keyWord);
        return customerInfoResponses;
    }


}
