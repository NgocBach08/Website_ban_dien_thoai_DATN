package com.example.testhtml.service;

import com.example.testhtml.dto.request.category.CategoryReqDto;
import com.example.testhtml.dto.respone.category.CategoryDTO;
import com.example.testhtml.dto.respone.category.CategoryResponeDto;
import com.example.testhtml.entity.CategoryEntity;

import java.util.List;

public interface ICategoryService {
    List<CategoryResponeDto> getAllCategory();
    CategoryEntity findById(String id);
    void saveCategory(CategoryReqDto categoryDto);
    String updateCategory(CategoryDTO categoryDto);
    void deleteCategory(CategoryEntity categoryEntity);
}
