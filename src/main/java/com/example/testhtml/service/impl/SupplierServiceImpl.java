package com.example.testhtml.service.impl;


import com.example.testhtml.constant.ConstansErrorCode;
import com.example.testhtml.dto.request.supplier.EditSupplierDto;
import com.example.testhtml.dto.request.supplier.SupplierRequestDTO;
import com.example.testhtml.dto.respone.supplier.SupplierResponseDTO;
import com.example.testhtml.entity.SupplierEntity;
import com.example.testhtml.exception.WorldPhoneExp;
import com.example.testhtml.repo.SupplierRepo;
import com.example.testhtml.service.ISupplierService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements ISupplierService {

    private final SupplierRepo supplierRepo;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<SupplierResponseDTO> findAllByDeleteFlagFalse() {
        List<SupplierEntity> entityList = supplierRepo.findByDeleteFlagIsFalse();
        List<SupplierResponseDTO> dtoList = new ArrayList<>();
        for (SupplierEntity a : entityList){
            dtoList.add(modelMapper.map(a,SupplierResponseDTO.class));
        }
        return dtoList;
    }

    @Override
    public List<SupplierResponseDTO> findAll() {
        List<SupplierEntity> entityList = supplierRepo.findAll();
        List<SupplierResponseDTO> dtoList = new ArrayList<>();
        for (SupplierEntity a : entityList){
            dtoList.add(modelMapper.map(a,SupplierResponseDTO.class));
        }
        return dtoList;    }

    @Override
    public Integer findByEmail(String email) {
        Optional<SupplierEntity> s = supplierRepo.findByEmail(email);
        if(s.isPresent()){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer findByPhone(String phone) {
        Optional<SupplierEntity> s = supplierRepo.findByPhoneNumber(phone);
        if(s.isPresent()){
            return 1;
        }
        return 0;
    }

    @Override
    public SupplierResponseDTO findById(Long id) {
        Optional<SupplierEntity> s = supplierRepo.findByIdAndDeleteFlagIsFalse(id);
        if(s.isPresent()){
            return modelMapper.map(s.get(), SupplierResponseDTO.class);
        }
            throw new  WorldPhoneExp(ConstansErrorCode.SUPPLIER_NOT_EXIST);
    }

    @Override
    public SupplierRequestDTO addSupplier(SupplierRequestDTO requestDTO) {
        if(findByEmail(requestDTO.getEmail()) == 0 && findByPhone(requestDTO.getPhoneNumber()) == 0){
            SupplierEntity entity = modelMapper.map(requestDTO, SupplierEntity.class);
            entity.setStatus("1");
            entity = supplierRepo.save(entity);
            return requestDTO;
        }
        return null;
    }

    @Override
    public EditSupplierDto updateSupplier(EditSupplierDto requestDTO) {
        Optional<SupplierEntity> entity = supplierRepo.findByIdAndDeleteFlagIsFalse(requestDTO.getId());
        if(entity.isPresent()){
            if(!entity.get().getPhoneNumber().equals(requestDTO.getPhoneNumber())){
                if(findByPhone(requestDTO.getPhoneNumber()) == 1){
                    throw new WorldPhoneExp(ConstansErrorCode.SUPPLIER_PHONE);
                }
            }else {
                entity.get().setAddress(requestDTO.getAddress());
                entity.get().setNote(requestDTO.getNote());
                entity.get().setName(requestDTO.getName());
                entity.get().setPhoneNumber(requestDTO.getPhoneNumber());
                supplierRepo.save(entity.get());
                return requestDTO;
            }
        }
        return null;
    }

    @Override
    public boolean deleteSupplier(Long id) {
        Optional<SupplierEntity> entity = supplierRepo.findByIdAndDeleteFlagIsFalse(id);
        if(!entity.isPresent()){
            throw new WorldPhoneExp(ConstansErrorCode.SUPPLIER_NOT_EXIST);
        }else {
            entity.get().setDeleteFlag(true);
            supplierRepo.save(entity.get());
            return true;
        }

    }

    @Override
    public boolean changeStatus(Long id, String status) {
        Optional<SupplierEntity> entity = supplierRepo.findByIdAndDeleteFlagIsFalse(id);
        if(!entity.isPresent()){
            throw new WorldPhoneExp(ConstansErrorCode.SUPPLIER_NOT_EXIST);
        }else {
            entity.get().setStatus(status);
            supplierRepo.save(entity.get());
            return true;
        }
    }
}
