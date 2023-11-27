package com.example.testhtml.service.impl;

import com.example.testhtml.repo.CustomerRepo;
import com.example.testhtml.service.ICustomerService;
import com.example.testhtml.dto.request.Customer.CustomerRequest;
import com.example.testhtml.dto.respone.Customer.CustomerInfoResponse;
import com.example.testhtml.dto.respone.Customer.CustomerServiceResponse;
import com.example.testhtml.entity.CustomerEntity;
import com.example.testhtml.service.ICustomerService;
import com.example.testhtml.type.CommonTypeMethod;
import com.example.testhtml.type.CustomerStatus;
import com.example.testhtml.until.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Description:
 *
 * @author: POLY_DuyDV
 * @version: 1.0
 * @since: 2/27/2022
 * Project_name: Tech-cam
 */
@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    private final ModelMapper MODEL_MAPPER = new ModelMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    private static final String NUMBER = "[0-9]*";


    @Override
    public List<CustomerInfoResponse> findCustomersByAll(String keyword) {
        Type customersType = new TypeToken<List<CustomerInfoResponse>>() {
        }.getType();
        List<CustomerEntity> customerEntities = customerRepo.findByKeyword(keyword);
        if (!CollectionUtils.isEmpty(customerEntities)) {
            return MODEL_MAPPER.map(customerEntities, customersType);
        }
        return Collections.emptyList();
    }

}
