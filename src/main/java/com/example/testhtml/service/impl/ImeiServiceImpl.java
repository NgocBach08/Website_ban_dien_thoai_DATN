package com.example.testhtml.service.impl;

import com.example.testhtml.common.StatusImei;
import com.example.testhtml.dto.request.imei.ImeiRequest;
import com.example.testhtml.dto.respone.imei.ImeiResponse;
import com.example.testhtml.entity.ImeiEntity;
import com.example.testhtml.repo.ImeiRepo;
import com.example.testhtml.service.ImeiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: hieu
 * @since: 29/10/2022
 * Project_name: com.example.testhtml.service.impl
 */
@Service
public class ImeiServiceImpl implements ImeiService {
    @Autowired
    private ImeiRepo imeiRepo;

    @Override
    public boolean saveImei(List<ImeiRequest> list, Long idProductOrder) {
        List<ImeiEntity> imeiEntityList = imeiRepo.findByOrder(idProductOrder);
        for (ImeiEntity ent: imeiEntityList
             ) {
            ent.setOrderDetailId(null);
            ent.setStatus(StatusImei.CHUA_BAN.getValue());
        }
        imeiRepo.saveAll(imeiEntityList);
        List<ImeiEntity> list1 = new ArrayList<>();
        for (ImeiRequest request: list
             ) {
            ImeiEntity entity = imeiRepo.findByIdAndDeleteFlagIsFalse(Long.valueOf(request.getImeiId()));
            entity.setStatus(StatusImei.DA_CO_DON.getValue());
            entity.setOrderDetailId(idProductOrder);
            list1.add(entity);
        }
        imeiRepo.saveAll(list1);
        return true;
    }

    @Override
    public List<ImeiResponse> findImeiDaBan(Long product, Long order) {
        if(imeiRepo.findImeiDaBan(product, order) != null ){
            return imeiRepo.findImeiDaBan(product, order).stream().map(this::maptoDto).collect(Collectors.toList());
        }
        return null;
    }

    private ImeiResponse maptoDto(ImeiEntity entity){
        return new ImeiResponse(String.valueOf(entity.getId()), entity.getValue());
    }
}
