package com.example.testhtml.service.impl;//package com.example.testhtml.service.impl;
//
//
//import com.example.testhtml.dto.request.attribute.rom_value.Rom_ValueRequest;
//import com.example.testhtml.dto.respone.attribute.rom_value.Rom_ValueRespone;
//import com.example.testhtml.entity.LoaiRomEntity;
//import com.example.testhtml.entity.RomValueEntity;
//import com.example.testhtml.repo.RomValueRepo;
//import com.example.testhtml.service.IRomValueService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class Rom_ValueServiceImpl implements IRomValueService {
//    private final RomValueRepo repo;
//
//    @Override
//    public List<Rom_ValueRespone> findAll() {
//        List<RomValueEntity> entities = repo.findByDeleteFlagIsFalse();
//        List<Rom_ValueRespone> list = new ArrayList<>();
//        for (RomValueEntity e : entities) {
//            list.add(new Rom_ValueRespone(e.getId(), e.getName(), e.getLoaiRomEntity().getId()));
//        }
//        return list;
//    }
//
//    @Override
//    public String save(Rom_ValueRequest request) {
//        RomValueEntity entity = new RomValueEntity();
//        LoaiRomEntity loaiRomEntity= new LoaiRomEntity();
//        loaiRomEntity.setId(request.getLoaiRomId());
//        entity.setId(request.getId());
//        entity.setLoaiRomEntity(loaiRomEntity);
//        entity.setName(request.getName());
//        entity.setCreateBy("ADMIN");
//        entity.setCreateDate(new Timestamp(System.currentTimeMillis()));
//        entity.setModifierDate(new Timestamp(System.currentTimeMillis()));
//        entity.setModifierBy("ADMIN");
//        entity.setDeleteFlag(false);
//        repo.save(entity);
//        return "ok";
//    }
//
//    @Override
//    public String update(Rom_ValueRequest request) {
//        RomValueEntity entity = repo.getById(request.getId());
////        entity.setLoai(request.getLoai());
//        LoaiRomEntity loaiRomEntity = new LoaiRomEntity();
//        loaiRomEntity.setId(request.getLoaiRomId());
//        entity.setLoaiRomEntity(loaiRomEntity);
//        entity.setName(request.getName());
//        repo.save(entity);
//        return "ok";
//    }
//
//    @Override
//    public Rom_ValueRespone findByCate(String id) {
//        RomValueEntity entity = repo.getById(Long.valueOf(id));
//        return new Rom_ValueRespone(entity.getId(), entity.getName(), entity.getLoaiRomEntity().getId());
//    }
//
//    @Override
//    public String delete(Long id) {
//        RomValueEntity entity = repo.getById(id);
//        entity.setDeleteFlag(true);
//        repo.save(entity);
//        return "ok";
//    }
//}
