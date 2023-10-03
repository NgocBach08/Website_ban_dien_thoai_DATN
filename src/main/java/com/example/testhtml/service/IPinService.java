package com.example.testhtml.service;

import com.example.testhtml.dto.request.attribute.pin.PinRequest;
import com.example.testhtml.dto.respone.attribute.pin.PinRespone;

import java.util.List;

public interface IPinService {

    List<PinRespone> findAll();

    String addPin(PinRequest request);

    String editPin(PinRequest request);

    String delete(Long id);

    PinRespone findById(Long id);
}
