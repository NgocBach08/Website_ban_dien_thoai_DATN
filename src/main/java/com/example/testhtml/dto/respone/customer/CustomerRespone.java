package com.example.testhtml.dto.respone.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author: hieu
 * @since: 24/08/2022
 * Project_name: com.example.testhtml.dto.respone.customer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRespone {
    private String name;
    private String phone;
    private String birthDay;
    private String email;
}
