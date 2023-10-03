package com.example.testhtml.service;

import com.example.testhtml.dto.request.imei.ImeiRequest;
import com.example.testhtml.dto.respone.imei.ImeiResponse;

import java.util.List;

/**
 * Description:
 *
 * @author: hieu
 * @since: 29/10/2022
 * Project_name: com.example.testhtml.service
 */
public interface ImeiService {
    boolean saveImei(List<ImeiRequest> list, Long idProductOrder);

    List<ImeiResponse> findImeiDaBan(Long product, Long order);
}
