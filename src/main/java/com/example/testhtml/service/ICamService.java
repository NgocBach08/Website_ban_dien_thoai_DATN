package com.example.testhtml.service;

import com.example.testhtml.dto.request.attribute.cam.CamRequest;
import com.example.testhtml.dto.respone.attribute.cam.CamRespone;

import java.util.List;


public interface ICamService {
    List<CamRespone> findAll();

    String save(CamRequest request);

    String update(CamRequest request);

    String delete(Long id);

    CamRespone findByID(Long id);
}
