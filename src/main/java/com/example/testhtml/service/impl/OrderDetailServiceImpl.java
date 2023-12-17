package com.example.testhtml.service.impl;

import com.example.testhtml.common.StatusImei;
import com.example.testhtml.common.StatusOrder;
import com.example.testhtml.dto.respone.order_detail.OrderDetailRespone;
import com.example.testhtml.entity.*;
import com.example.testhtml.repo.*;
import com.example.testhtml.service.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    private final VoucherRepo voucherRepo;
    @Override
    public String deleteDetail(Long id, String codeOrder) {
        OrdersDetailEntity detailEntity = detailRepo.findByDeleteFlagIsFalseAndId(id);
        OrdersEntity entity = detailEntity.getOrdersEntity();
        List<OrdersDetailEntity> ordersDetailEntities = detailRepo.findByDeleteFlagIsFalseAndOrdersEntity(entity);
        Long tongTien = 0L;
        for (OrdersDetailEntity detail: ordersDetailEntities
        ) {
            tongTien+=(detail.getPrice()* detail.getQuantity());
        }
        if (tongTien - (detailEntity.getQuantity() * detailEntity.getPrice()) < 0) {
            tongTien = 0L;
        } else {
            tongTien = tongTien - (detailEntity.getQuantity() * detailEntity.getPrice());
        }
        if (entity != null && entity.getTotalMoney() != null ) {
            Optional<VoucherEntity> voucherEntity = voucherRepo.findById(String.valueOf(entity.getVoucherID()));
            if (voucherEntity.isPresent() && tongTien > 0L) {
                if(voucherEntity != null){
                    if(voucherEntity.get().getTypeDiscount().equals("đ")){
                        tongTien = tongTien - voucherEntity.get().getDiscount();
                    }else {
                        tongTien = tongTien - (tongTien / 100 * voucherEntity.get().getDiscount());
                    }
                }
            }
            entity.setTotalMoney(tongTien);
            ordersRepo.save(entity);
        }
        detailEntity.setDeleteFlag(true);
        ProductPropertyEntity entityPro = propertyProductRepo.findById(detailEntity.getIdPropertyProduct()).get();
        List<ImeiEntity> imeiEntityList = imeiRepo.findByOrderDetailIdAndPropertyProductId(detailEntity.getId(), entityPro.getId());
        if (entity.getStatus().equals(String.valueOf(StatusOrder.CHO_GIAO_HANG.getIndex()))) {
            entityPro.setQuantity(detailEntity.getQuantity() + entityPro.getQuantity());
        }
        imeiEntityList.forEach(x -> x.setStatus(StatusImei.CHUA_BAN.getValue()));
        imeiRepo.saveAll(imeiEntityList);
        propertyProductRepo.save(entityPro);
        detailRepo.save(detailEntity);
        return "ok";
    }
}
