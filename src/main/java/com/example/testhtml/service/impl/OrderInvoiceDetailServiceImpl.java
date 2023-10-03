package com.example.testhtml.service.impl;


import com.example.testhtml.common.StatusOrderInvoice;
import com.example.testhtml.common.StatusOrderInvoiceDetail;
import com.example.testhtml.constant.ConstansErrorCode;
import com.example.testhtml.dto.request.orderinvoicedetail.OrderInvoiceDetailRequest;
import com.example.testhtml.dto.respone.order_invoice.detail.OrderInvoiceDetailRespone;
import com.example.testhtml.entity.*;
import com.example.testhtml.exception.WorldPhoneExp;
import com.example.testhtml.repo.*;
import com.example.testhtml.service.IOrderInvoiceDetailService;
import com.example.testhtml.until.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderInvoiceDetailServiceImpl implements IOrderInvoiceDetailService {
    private final RomRepo romRepo;
    private final ColorRepo colorRepo;

    private final InvoiceOrderDetailRepo detailRepo;

    private final SessionUtil sessionUtil;

    private final InvoiceOrderRepo orderRepo;

    private final PropertyProductRepo propertyProductRepo;



    @Override
    public String createOrderInvoiceDetail(List<OrderInvoiceDetailRequest> list, InvoiceOrderEntity entity) {
        for (OrderInvoiceDetailRequest req: list
             ) {
            InvoiceOrderDetailEntity entityDetail = mapToEntity(req);
            entityDetail.setInvoiceOrderEntity(entity);
            try {
                InvoiceOrderDetailEntity temp = detailRepo.save(entityDetail);
            }catch (Exception e){
                log.error("Error add order detail : {}", e.getMessage());
                entity.setDeleteFlag(true);
                orderRepo.save(entity);
                return "false";
            }
        }
        return "ok";
    }

    @Override
    public String updateOrderInvoiceDetail(List<OrderInvoiceDetailRequest> list, InvoiceOrderEntity entity) {
        List<InvoiceOrderDetailEntity> entityList = detailRepo.findByInvoiceOrderEntity(entity.getId());
        for (OrderInvoiceDetailRequest req: list
        ) {
            InvoiceOrderDetailEntity entityDetail = mapToEntity(req);
            entityDetail.setInvoiceOrderEntity(entity);
            boolean check = true;
            for (InvoiceOrderDetailEntity e: entityList
                 ) {
                if(e.getColorEntity().getId() == entityDetail.getColorEntity().getId() && e.getRomEntity().getId() == entityDetail.getRomEntity().getId()){
                    entityDetail.setId(e.getId());
                    detailRepo.save(entityDetail);
                    check = false;
                }
            }
            if(check){
                detailRepo.save(entityDetail);
            }

        }
        for (InvoiceOrderDetailEntity e: entityList
        ) {
            boolean check = true;
            for (OrderInvoiceDetailRequest req: list
            ) {
                if(String.valueOf(e.getColorEntity().getId()).equals(String.valueOf(Long.valueOf(req.getColorId()))) && String.valueOf(e.getRomEntity().getId()).equals(String.valueOf(Long.valueOf(req.getRomId()))) ){
                    check = false;
                }

            }
            if(check){
                e.setDeleteFlag(true);
                detailRepo.save(e);
            }
        }
        return "ok";
    }

    @Override
    public List<OrderInvoiceDetailRespone> findByOrderInvoice(Long id) {
        List<InvoiceOrderDetailEntity> entitie = detailRepo.findByInvoiceOrderEntity(id);
        List<OrderInvoiceDetailRespone> respones = new ArrayList<>();
        for (InvoiceOrderDetailEntity e: entitie
             ) {
            respones.add(mapToRespone(e));
        }
        return respones;
    }

    @Override
    public String createNhapDetail(List<OrderInvoiceDetailRequest> list, InvoiceOrderEntity entity) {
        List<InvoiceOrderDetailEntity> entityList = detailRepo.findByInvoiceOrderEntity(entity.getId());

            for (OrderInvoiceDetailRequest req: list
            ) {
                InvoiceOrderDetailEntity entityDetail = mapToEntity(req);
                if(!entityDetail.getStatus().equals(String.valueOf(StatusOrderInvoiceDetail.DA_NHAP.getIndex()))){
                    entity.setStatus(StatusOrderInvoice.GIAO_THIEU.getIndex());
                    entity = orderRepo.save(entity);
                }
                entityDetail.setInvoiceOrderEntity(entity);
                entityDetail.setMoneyInvoice(req.getMoneyInvoice() == null ? 0 : req.getMoneyInvoice());
                boolean check = true;
                for (InvoiceOrderDetailEntity e: entityList
                ) {
                    if(String.valueOf(e.getColorEntity().getId()).equals(String.valueOf(entityDetail.getColorEntity().getId()))
                            && String.valueOf(e.getRomEntity().getId()).equals(String.valueOf(entityDetail.getRomEntity().getId()))){

                        entityDetail.setId(e.getId());
                        detailRepo.save(entityDetail);
                        if (entityDetail.getStatus().equals(String.valueOf(StatusOrderInvoiceDetail.DA_NHAP.getIndex()))){
                            List<ProductPropertyEntity> list1 = propertyProductRepo.findByRomAndColor(e.getRomEntity().getId(), e.getColorEntity().getId());
                            if(list1.size() == 0){
                                ProductPropertyEntity propertyEntity = new ProductPropertyEntity();
                                propertyEntity.setQuantity(entityDetail.getQuantityInvoice());
                                propertyEntity.setPrice(0);
                                propertyEntity.setStatus("OFF");
                                propertyEntity.setRomEntity(entityDetail.getRomEntity());
                                propertyEntity.setColorEntity(entityDetail.getColorEntity());
                                propertyProductRepo.save(propertyEntity);
                            }else {
                                list1.get(0).setQuantity(list1.get(0).getQuantity() + entityDetail.getQuantityInvoice());
                                propertyProductRepo.save(list1.get(0));
                            }
                            check = false;
                        }
                        break;
                    }
                }
                if(check){
                    List<ProductPropertyEntity> list1 = propertyProductRepo.findByRomAndColor(entityDetail.getRomEntity().getId(), entityDetail.getColorEntity().getId());
                    if (entityDetail.getStatus().equals(String.valueOf(StatusOrderInvoiceDetail.DA_NHAP.getIndex()))){
                        if(list1.size() == 0){
                            ProductPropertyEntity propertyEntity = new ProductPropertyEntity();
                            propertyEntity.setQuantity(entityDetail.getQuantityInvoice());
                            propertyEntity.setPrice(0);
                            propertyEntity.setStatus("OFF");
                            propertyEntity.setRomEntity(entityDetail.getRomEntity());
                            propertyEntity.setColorEntity(entityDetail.getColorEntity());
                            propertyProductRepo.save(propertyEntity);
                        }else {
                            list1.get(0).setQuantity(list1.get(0).getQuantity() + entityDetail.getQuantityInvoice());
                            propertyProductRepo.save(list1.get(0));
                        }
                    }
                    detailRepo.save(entityDetail);
                }

            }

        for (InvoiceOrderDetailEntity e: entityList
        ) {
            boolean check = true;
            for (OrderInvoiceDetailRequest req: list
            ) {
                if(String.valueOf(e.getColorEntity().getId()).equals(String.valueOf(Long.valueOf(req.getColorId())))  && String.valueOf(e.getRomEntity().getId()).equals(String.valueOf(Long.valueOf(req.getRomId())))){
                    check = false;
                    break;
                }
            }
            if(check){
                e.setDeleteFlag(true);
                detailRepo.save(e);
            }
        }
            return "ok";
    }

    @Override
    public String updateNhapDetail(List<OrderInvoiceDetailRequest> list, InvoiceOrderEntity entity) {
        List<InvoiceOrderDetailEntity> entityList = detailRepo.findByInvoiceOrderEntity(entity.getId());
        boolean checkStatus = true;
        for (OrderInvoiceDetailRequest req: list
        ) {
            InvoiceOrderDetailEntity entityDetail = mapToEntity(req);
            entityDetail.setInvoiceOrderEntity(entity);
            entityDetail.setMoneyInvoice(req.getMoneyInvoice());
            boolean check = true;
            if(!req.getStatus().equals(String.valueOf(StatusOrderInvoiceDetail.DA_NHAP.getIndex()))){
                checkStatus = false;
            }
            for (InvoiceOrderDetailEntity e: entityList
            ) {

                if(e.getColorEntity().getId() == entityDetail.getColorEntity().getId() && e.getRomEntity().getId() == entityDetail.getRomEntity().getId()){
                    if(e.getStatus().equals(String.valueOf(StatusOrderInvoiceDetail.DA_NHAP.getIndex()))){
                        entityDetail.setId(e.getId());
                        entityDetail.setQuantityInvoice(e.getQuantityInvoice());
                        entityDetail.setStatus(e.getStatus());
                        if(entityDetail.getMoneyInvoice() <= 0){
                            entityDetail.setMoneyInvoice(e.getMoneyInvoice());
                        }
                        detailRepo.save(entityDetail);
                        check = false;
                        break;
                    }
                    if (entityDetail.getStatus().equals(String.valueOf(StatusOrderInvoiceDetail.DA_NHAP.getIndex()))){
                        List<ProductPropertyEntity> list1 = propertyProductRepo.findByRomAndColor(e.getRomEntity().getId(), e.getColorEntity().getId());
                        if(list1.size() > 0 && req.getStatus().equals(String.valueOf(StatusOrderInvoiceDetail.DA_NHAP.getIndex()))){
                            list1.get(0).setQuantity(list1.get(0).getQuantity() - e.getQuantityInvoice() + req.getQuantityInvoice());
                            propertyProductRepo.save(list1.get(0));
                        }
                        if (list1.size() == 0){
                            ProductPropertyEntity propertyEntity = new ProductPropertyEntity();
                            propertyEntity.setQuantity(entityDetail.getQuantityInvoice());
                            propertyEntity.setPrice(0);
                            propertyEntity.setStatus("OFF");
                            propertyEntity.setRomEntity(entityDetail.getRomEntity());
                            propertyEntity.setColorEntity(entityDetail.getColorEntity());
                            propertyProductRepo.save(propertyEntity);
                        }
                    }
                    entityDetail.setId(e.getId());
                    detailRepo.save(entityDetail);
                    check = false;
                    break;
                }
            }
            if(check){
                if (entityDetail.getStatus().equals(String.valueOf(StatusOrderInvoiceDetail.DA_NHAP.getIndex()))){
                    List<ProductPropertyEntity> list1 = propertyProductRepo.findByRomAndColor(entityDetail.getRomEntity().getId(), entityDetail.getColorEntity().getId());
                    if(list1.size() == 0){
                        ProductPropertyEntity propertyEntity = new ProductPropertyEntity();
                        propertyEntity.setQuantity(entityDetail.getQuantityInvoice());
                        propertyEntity.setPrice(0);
                        propertyEntity.setStatus("OFF");
                        propertyEntity.setRomEntity(entityDetail.getRomEntity());
                        propertyEntity.setColorEntity(entityDetail.getColorEntity());
                        propertyProductRepo.save(propertyEntity);
                    }else {
                        list1.get(0).setQuantity(list1.get(0).getQuantity() + entityDetail.getQuantityInvoice());
                        propertyProductRepo.save(list1.get(0));
                    }
                }

                detailRepo.save(entityDetail);
            }

        }
        if(checkStatus){
            entity.setStatus(StatusOrderInvoice.DA_NHAN.getIndex());
            orderRepo.save(entity);
        }else {
            entity.setStatus(StatusOrderInvoice.GIAO_THIEU.getIndex());
            orderRepo.save(entity);
        }
        for (InvoiceOrderDetailEntity e: entityList
        ) {
            boolean check = true;
            for (OrderInvoiceDetailRequest req: list
            ) {
                if(String.valueOf(e.getColorEntity().getId()).equals(String.valueOf(Long.valueOf(req.getColorId())))  && String.valueOf(e.getRomEntity().getId()).equals(String.valueOf(Long.valueOf(req.getRomId())))){
                    check = false;
                    break;
                }
            }
            if(check){
                List<ProductPropertyEntity> list1 = propertyProductRepo.findByRomAndColor(e.getRomEntity().getId(), e.getColorEntity().getId());

               if(list1 != null && list1.size() > 0 && !e.getStatus().equals(String.valueOf(StatusOrderInvoiceDetail.DA_NHAP.getIndex()))){
                   list1.get(0).setQuantity(list1.get(0).getQuantity() - e.getQuantityInvoice());
                   if(list1.get(0).getQuantity() <= 0){
                       list1.get(0).setStatus("OFF");
                       list1.get(0).setQuantity(0);
                   }
                   propertyProductRepo.save(list1.get(0));
                   e.setDeleteFlag(true);
                   detailRepo.save(e);
               }
            }
        }
        return "ok";
    }


    private OrderInvoiceDetailRespone mapToRespone(InvoiceOrderDetailEntity entity){
        OrderInvoiceDetailRespone respone = new OrderInvoiceDetailRespone();
        if(entity.getMoneyInvoice() != null){
            respone.setPriceProduce(String.valueOf(entity.getMoneyInvoice()));
        }
        respone.setColorId(String.valueOf(entity.getColorEntity().getId()));
        respone.setProductRomID(String.valueOf(entity.getRomEntity().getId()));
        respone.setProductName(entity.getRomEntity().getProductEntity().getName());
        respone.setQuantityProduct(String.valueOf(entity.getQuantityInvoice()));
        respone.setColorName(entity.getColorEntity().getValueColor());
        respone.setProductRomName(entity.getRomEntity().getName());
        respone.setNote(entity.getNote());
        respone.setStatus(entity.getStatus());
        return respone;
    }

    private InvoiceOrderDetailEntity mapToEntity(OrderInvoiceDetailRequest request){
        InvoiceOrderDetailEntity entity = new InvoiceOrderDetailEntity();
        entity.setQuantityInvoice(request.getQuantityInvoice());

        // Tìm theo mã màu
        ColorEntity colorEntity = colorRepo.findByIdAndDeleteFlagIsFalse(Long.valueOf(request.getColorId()));
        if(colorEntity == null){
            throw new WorldPhoneExp(ConstansErrorCode.COLOR_NOT_EXIST);
        }
        entity.setColorEntity(colorEntity);

        // tìm theo bộ nhớ trong của điện thoại
        RomEntity romEntity = romRepo.findByIdAndDeleteFlagIsFalse(Long.valueOf(request.getRomId()));
        if(romEntity == null){
            throw new WorldPhoneExp(ConstansErrorCode.ROM_NOT_EXIST);
        }
        entity.setStatus(request.getStatus());
        entity.setNote(request.getNote());
        entity.setRomEntity(romEntity);
        entity.setCreateBy("ADMIN");
        entity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        entity.setModifierDate(new Timestamp(System.currentTimeMillis()));
        entity.setModifierBy((String) sessionUtil.getObject("username"));
        entity.setDeleteFlag(false);
        return entity;
    }
}
