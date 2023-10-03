package com.example.testhtml.service;

import com.example.testhtml.dto.request.attribute.AttributeRequestAdd;
import com.example.testhtml.dto.request.attribute.AttributeRequestEdit;
import com.example.testhtml.dto.respone.attribute.AttributeRespone;

public interface AttributeProductService {
    AttributeRespone findByProduct(Long id);

    String saveAttribute(AttributeRequestAdd requestAdd, Long productId) ;

    String updateAttribute(AttributeRequestEdit attributeRequestEdit, Long productId);
}
