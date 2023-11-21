package com.example.testhtml.api;

import com.example.testhtml.dto.request.Customer.CustomerRequest;
import com.example.testhtml.dto.respone.Customer.CustomerInfoResponse;
import com.example.testhtml.dto.respone.Customer.CustomerResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Description:
 *
 * @version: 1.0
 * @since: 1/23/2022
 */

@RestController
@RequestMapping("api/v1/customer")
public interface CustomerApi {


    @GetMapping("search/{keyWord}")
    List<CustomerInfoResponse> findCustomersByAll(@PathVariable String keyWord);


}
