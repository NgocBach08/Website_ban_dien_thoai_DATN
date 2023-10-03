package com.example.testhtml.service.impl;


import com.example.testhtml.dto.request.attribute.loai_screen.Loai_ScreenRequest;
import com.example.testhtml.dto.respone.attribute.loai_screen.Loai_ScreenRespone;
import com.example.testhtml.entity.LoaiScreenEntity;
import com.example.testhtml.repo.LoaiScreenRepo;
import com.example.testhtml.service.ILoaiScreenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class Loai_ScreenServiceImpl implements ILoaiScreenService {
    private final LoaiScreenRepo repo;


    @Override
    public List<Loai_ScreenRespone> findAll() {
        List<Loai_ScreenRespone> list = new ArrayList<>();
        List<LoaiScreenEntity> entities = repo.findByDeleteFlagIsFalse();
        for (LoaiScreenEntity e : entities
        ) {
            list.add(new Loai_ScreenRespone(String.valueOf(e.getId()), e.getName()));
        }
        return list;
    }

    @Override
    public Loai_ScreenRespone findById(String id) {
        LoaiScreenEntity loaiscreen = repo.getById(Long.valueOf(id));
        return new Loai_ScreenRespone(String.valueOf(loaiscreen.getId()), loaiscreen.getName());
    }

    @Override
    public String createLoaiScreen(Loai_ScreenRequest request) {
        LoaiScreenEntity loaiscreen = new LoaiScreenEntity();
        loaiscreen.setName(request.getName());
        repo.save(loaiscreen);
        return "ok";
    }

    @Override
    public String updateLoaiScreen(Loai_ScreenRequest request) {
        LoaiScreenEntity loaiscreen = new LoaiScreenEntity();
        loaiscreen.setName(request.getName());
        loaiscreen.setId(Long.valueOf(request.getId()));
        repo.save(loaiscreen);
        return "ok";
    }

    @Override
    public String deleteLoaiScreen(Long id) {
        LoaiScreenEntity loaiscreen = repo.getById(id);
        loaiscreen.setDeleteFlag(true);
        repo.save(loaiscreen);
        return "ok";
    }
}
