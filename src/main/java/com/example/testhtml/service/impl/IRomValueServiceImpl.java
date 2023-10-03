package com.example.testhtml.service.impl;

import com.example.testhtml.dto.request.attribute.rom.RomRequest;
import com.example.testhtml.dto.respone.attribute.rom.RomRespone;
import com.example.testhtml.entity.LoaiRomEntity;
import com.example.testhtml.entity.RomEntity;
import com.example.testhtml.entity.RomValueEntity;
import com.example.testhtml.repo.LoaiRomRepo;
import com.example.testhtml.repo.RomRepo;
import com.example.testhtml.repo.RomValueRepo;
import com.example.testhtml.service.IRomValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author: hieu
 * @since: 07/07/2022
 * Project_name: com.example.testhtml.service.impl
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IRomValueServiceImpl implements IRomValueService {
    private final RomValueRepo repo;

    private final RomRepo romRepo;

    private final LoaiRomRepo loaiRomRepo;
    @Override
    public List<RomRespone> findAll() {
        List<RomRespone> list = new ArrayList<>();
        List<RomValueEntity> entities = repo.findByDeleteFlagIsFalse();
        for (RomValueEntity e: entities
             ) {
            if(!e.getLoaiRomEntity().isDeleteFlag()){
                list.add(new RomRespone(e.getId(), e.getName() + " " + e.getLoaiRomEntity().getName(), e.getLoaiRomEntity().getId()));
            }

        }
        return list;
    }

    @Override
    public String save(RomRequest request) {
        RomValueEntity entity = new RomValueEntity();
        LoaiRomEntity loaiRomEntity = new LoaiRomEntity();
        loaiRomEntity.setId(request.getLoaiRomId());
        entity.setId(request.getId());
        entity.setLoaiRomEntity(loaiRomEntity);
        entity.setName(request.getName());
        repo.save(entity);
        return "ok";
    }

    @Override
    public String update(RomRequest request) {
        RomValueEntity entity = repo.getById(request.getId());
        String old = entity.getName() + " " + entity.getLoaiRomEntity().getName();
        LoaiRomEntity loaiRomEntity = loaiRomRepo.getById(request.getLoaiRomId());
        entity.setLoaiRomEntity(loaiRomEntity);
        entity.setName(request.getName());
        String newName = entity.getName() + " " + loaiRomEntity.getName();
        List<RomEntity> list = romRepo.findByName(old);
        for (RomEntity entity1 : list){
            entity1.setName(newName);
            romRepo.save(entity1);
        }

        repo.save(entity);
        return "ok";
    }

    @Override
    public String delete(Long id) {
        RomValueEntity entity = repo.getById(id);
        entity.setDeleteFlag(true);
        repo.save(entity);
        return "ok";
    }

    @Override
    public RomRespone findById(Long id) {
        RomValueEntity entity = repo.getById(id);
        return new RomRespone(entity.getId(), entity.getName(),entity.getLoaiRomEntity().getId());
    }
}
