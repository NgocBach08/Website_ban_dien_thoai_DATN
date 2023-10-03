package com.example.testhtml.service;

import com.example.testhtml.dto.request.attribute.os.OsRequest;
import com.example.testhtml.dto.respone.attribute.os.OsRespone;

import java.util.List;

public interface IOsService {
    List<OsRespone> findAll();

    String save(OsRequest request);

    String edit(OsRequest request);

    OsRespone findById(String id);

    String delete(Long id);
}
