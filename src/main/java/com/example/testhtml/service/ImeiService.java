package com.example.testhtml.service;

import com.example.testhtml.dto.request.imei.ImeiRequest;
import com.example.testhtml.dto.respone.imei.ImeiResponse;

import java.util.List;


public interface ImeiService {
    boolean saveImei(List<ImeiRequest> list, Long idProductOrder);

    List<ImeiResponse> findImeiDaBan(Long product, Long order);
}
