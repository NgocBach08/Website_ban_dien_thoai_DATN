package com.example.testhtml.service;

import com.example.testhtml.dto.request.attribute.rom.RomRequest;
import com.example.testhtml.dto.request.rom.RomRequestAdd;
import com.example.testhtml.dto.respone.attribute.ram.RamRespone;
import com.example.testhtml.dto.respone.rom.RomRespone;
import com.example.testhtml.entity.ProductEntity;

import java.util.List;

public interface IRomService {
    List<RomRespone> findByProduct(Long id);

    String createRom(List<RomRequestAdd> romRequestAdds, ProductEntity entity);

    String createRomWithProductEdit(List<RomRequestAdd> romRequestAdds, ProductEntity entity);
    List<RomRespone> findAll();

    String saveRom(RomRequest request);

    String editRom(RomRequest request);

    RamRespone findById(String id);

    String deleteRom(Long id);
}
