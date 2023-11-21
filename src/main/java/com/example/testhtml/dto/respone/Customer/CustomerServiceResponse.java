package com.example.testhtml.dto.respone.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author: POLY_DuyDVPH12712
 * @version: 1.0
 * @since: 2/27/2022
 * Project_name: Tech-cam
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerServiceResponse {
   private Boolean saved;
   private Boolean existing;
   private Boolean emailExisting;
   private Boolean phoneNumberExisting;
   private CustomerInfoResponse customerInfoResponse;
}
