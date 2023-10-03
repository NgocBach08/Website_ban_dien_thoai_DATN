package com.example.testhtml.service;

import com.example.testhtml.dto.request.attribute.ram.RamRequest;
import com.example.testhtml.dto.respone.attribute.ram.RamRespone;

import java.util.List;

public interface IRamService {
    List<RamRespone> findAll();

    String save(RamRequest request);

    String edit(RamRequest request);

    String delete(Long id);

    RamRespone findById(String id);

}
