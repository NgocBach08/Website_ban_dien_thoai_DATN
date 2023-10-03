package com.example.testhtml.service;

import com.example.testhtml.dto.request.attribute.rom.RomRequest;
import com.example.testhtml.dto.respone.attribute.rom.RomRespone;

import java.util.List;

/**
 * Description:
 *
 * @author: hieu
 * @since: 07/07/2022
 * Project_name: com.example.testhtml.service
 */
public interface IRomValueService {
    List<RomRespone> findAll();
    String save(RomRequest request);
    String update(RomRequest request);
    String delete(Long id);
    RomRespone findById(Long id);
}
