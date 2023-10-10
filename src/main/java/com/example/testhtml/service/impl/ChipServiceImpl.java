package com.example.testhtml.service.impl;

import com.example.testhtml.dto.request.attribute.chip.ChipRequestDto;
import com.example.testhtml.dto.respone.attribute.chip.ChipRespone;
import com.example.testhtml.entity.ChipEntity;
import com.example.testhtml.repo.ChipRepo;
import com.example.testhtml.service.IChipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChipServiceImpl implements IChipService {
    private final ChipRepo repo;





    @Override
    public String deleteChip(Long id) {
        ChipEntity chip = repo.getById(id);
        chip.setDeleteFlag(true);
        repo.save(chip);
        return "ok";
    }
}
