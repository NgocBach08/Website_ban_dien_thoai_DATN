package com.example.testhtml.service;

import com.example.testhtml.dto.request.attribute.loai_os.Loai_OsRequest;
import com.example.testhtml.dto.respone.attribute.loai_os.Loai_OsRespone;

import java.util.List;

public interface ILoaiOsService {
    List<Loai_OsRespone> findAll();

    Loai_OsRespone findById(String id);

    String createLoaiOs(Loai_OsRequest request);

    String updateLoaiOs(Loai_OsRequest request);

    String deleteLoaiOs(Long id);
}
