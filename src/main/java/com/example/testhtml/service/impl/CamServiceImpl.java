package com.example.testhtml.service.impl;

import com.example.testhtml.dto.request.attribute.cam.CamRequest;
import com.example.testhtml.dto.respone.attribute.cam.CamRespone;
import com.example.testhtml.entity.CamEntity;
import com.example.testhtml.repo.CamRepo;
import com.example.testhtml.service.ICamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CamServiceImpl implements ICamService {
    private final CamRepo repo;

    @Override
    public List<CamRespone> findAll() {
        List<CamEntity> camEntities = repo.findByDeleteFlagIsFalse();
        List<CamRespone> respones = new ArrayList<>();
        for (CamEntity e: camEntities
        ) {
            respones.add(new CamRespone(e.getId(), e.getCamTruoc(), e.getCamSau()));
        }
        return respones;
    }

    @Override
    public String save(CamRequest request) {
        CamEntity entity = new CamEntity();
        entity.setCamTruoc(request.getNameTruoc());
        entity.setCamSau(request.getNameSau());
        repo.save(entity);
        return "ok";
    }

    @Override
    public String update(CamRequest request) {
        CamEntity entity = repo.getById(request.getId());
        entity.setCamTruoc(request.getNameTruoc());
        entity.setCamSau(request.getNameSau());
        repo.save(entity);
        return "ok";
    }

    @Override
    public String delete(Long id) {
        CamEntity entity = repo.getById(id);
        entity.setDeleteFlag(true);
        repo.save(entity);
        return "ok";
    }

    @Override
    public CamRespone findByID(Long id) {
        CamEntity entity = repo.getById(id);
        return new CamRespone(entity.getId(), entity.getCamTruoc(), entity.getCamSau());
    }
}
