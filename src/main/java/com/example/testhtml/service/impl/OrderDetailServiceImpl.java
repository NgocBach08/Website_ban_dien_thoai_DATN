package com.example.testhtml.service.impl;

import com.example.testhtml.entity.OrdersDetailEntity;
import com.example.testhtml.entity.ProductPropertyEntity;
import com.example.testhtml.repo.OrdersDetailRepo;
import com.example.testhtml.repo.OrdersRepo;
import com.example.testhtml.repo.PropertyProductRepo;
import com.example.testhtml.service.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements IOrderDetailService {
    private final OrdersDetailRepo detailRepo;

    private final OrdersRepo ordersRepo;

    private  final PropertyProductRepo propertyProductRepo;
    @Override
    public String deleteDetail(Long id, String codeOrder) {
        OrdersDetailEntity detailEntity = detailRepo.findByDeleteFlagIsFalseAndId(id);
        detailEntity.setDeleteFlag(true);
        ProductPropertyEntity entity = propertyProductRepo.findById(detailEntity.getIdPropertyProduct()).get();
        entity.setQuantity(detailEntity.getQuantity() + entity.getQuantity());
        propertyProductRepo.save(entity);
        detailRepo.save(detailEntity);
        return "ok";
    }
}
