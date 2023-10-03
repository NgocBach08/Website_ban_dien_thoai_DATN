package com.example.testhtml.service;

import com.example.testhtml.dto.request.attribute.loai_screen.Loai_ScreenRequest;
import com.example.testhtml.dto.respone.attribute.loai_screen.Loai_ScreenRespone;

import java.util.List;

public interface ILoaiScreenService {
    List<Loai_ScreenRespone> findAll();

    Loai_ScreenRespone findById(String id);

    String createLoaiScreen(Loai_ScreenRequest request);

    String updateLoaiScreen(Loai_ScreenRequest request);

    String deleteLoaiScreen(Long id);
}
