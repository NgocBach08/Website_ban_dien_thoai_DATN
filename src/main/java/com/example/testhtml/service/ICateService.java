package com.example.testhtml.service;

import com.example.testhtml.dto.respone.category.CategoryDTO;
import com.example.testhtml.dto.respone.category.CategoryResponeDto;

import java.util.List;

public interface ICateService {
    List<CategoryResponeDto> findAll();

    String save(CategoryDTO request);

    String edit(CategoryDTO request);

    CategoryResponeDto findById(String id);

    String delete(Long id);
}
