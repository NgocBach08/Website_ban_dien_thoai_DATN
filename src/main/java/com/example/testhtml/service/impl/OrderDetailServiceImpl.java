package com.example.testhtml.service.impl;

import com.example.testhtml.common.StatusImei;
import com.example.testhtml.entity.ImeiEntity;
import com.example.testhtml.entity.OrdersDetailEntity;
import com.example.testhtml.entity.OrdersEntity;
import com.example.testhtml.entity.ProductPropertyEntity;
import com.example.testhtml.repo.ImeiRepo;
import com.example.testhtml.repo.OrdersDetailRepo;
import com.example.testhtml.repo.OrdersRepo;
import com.example.testhtml.repo.PropertyProductRepo;
import com.example.testhtml.service.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @author: hieu
 * @since: 06/09/2022
 * Project_name: com.example.testhtml.service.impl
 */
@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements IOrderDetailService {
    private final OrdersDetailRepo detailRepo;

    private final OrdersRepo ordersRepo;

    private  final PropertyProductRepo propertyProductRepo;

    private final ImeiRepo imeiRepo;
    @Override
    public String deleteDetail(Long id, String codeOrder) {
        OrdersDetailEntity detailEntity = detailRepo.findByDeleteFlagIsFalseAndId(id);
        OrdersEntity entity = detailEntity.getOrdersEntity();
        if (entity != null && entity.getTotalMoney() != null) {
            entity.setTotalMoney(entity.getTotalMoney() - (detailEntity.getQuantity() * detailEntity.getPrice()));
            ordersRepo.save(entity);
        }
        detailEntity.setDeleteFlag(true);
        ProductPropertyEntity entityPro = propertyProductRepo.findById(detailEntity.getIdPropertyProduct()).get();
        List<ImeiEntity> imeiEntityList = imeiRepo.findByOrderDetailIdAndPropertyProductId(detailEntity.getId(), entityPro.getId());
        entityPro.setQuantity(detailEntity.getQuantity() + entityPro.getQuantity());
        imeiEntityList.forEach(x -> x.setStatus(StatusImei.CHUA_BAN.getValue()));
        imeiRepo.saveAll(imeiEntityList);
        propertyProductRepo.save(entityPro);
        detailRepo.save(detailEntity);
        return "ok";
    }
}
