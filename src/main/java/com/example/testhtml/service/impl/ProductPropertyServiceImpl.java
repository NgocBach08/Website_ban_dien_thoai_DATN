package com.example.testhtml.service.impl;


import com.example.testhtml.common.StatusImei;
import com.example.testhtml.constant.ConstansErrorCode;
import com.example.testhtml.constant.ConstansStatus;
import com.example.testhtml.dto.request.product_property.ProductPropertyRequest;
import com.example.testhtml.dto.respone.imei.ImeiResponse;
import com.example.testhtml.dto.respone.order_detail.OrderDetailRespone;
import com.example.testhtml.dto.respone.product.ProductPropertyRespone;
import com.example.testhtml.entity.ImeiEntity;
import com.example.testhtml.entity.ProductEntity;
import com.example.testhtml.entity.ProductPropertyEntity;
import com.example.testhtml.exception.WorldPhoneExp;
import com.example.testhtml.repo.ImeiRepo;
import com.example.testhtml.repo.PropertyProductRepo;
import com.example.testhtml.service.IProductPropertyService;
import com.example.testhtml.until.ConvertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductPropertyServiceImpl implements IProductPropertyService {
    private final PropertyProductRepo repo;
    private final ConvertUtil convertUtil;

    private final ImeiRepo imeiRepo;

    private final PropertyProductRepo propertyProductRepo;

    @Override
    public List<ProductPropertyRespone> findByRom(Long id) {
        List<ProductPropertyRespone> respones = new ArrayList<>();
        List<ProductPropertyEntity> entityList = repo.findByRom(id);
        for (ProductPropertyEntity a: entityList
             ) {
            respones.add(mapToDto(a));
        }
        return respones;
    }

    @Override
    public OrderDetailRespone findById(Long id) {
        OrderDetailRespone detailRespone = new OrderDetailRespone();
        ProductPropertyEntity propertyEntity = propertyProductRepo.getById(id);
        detailRespone.setIdProductProperty(String.valueOf(propertyEntity.getId()));
        ProductEntity productEntity = propertyEntity.getRomEntity().getProductEntity();
        detailRespone.setColorProduct(propertyEntity.getColorEntity().getValueColor());
        detailRespone.setRomProduct(propertyEntity.getRomEntity().getName());
        detailRespone.setNameProduct(productEntity.getName());
        detailRespone.setImageProduct(productEntity.getImage_key());
        detailRespone.setTonKho(String.valueOf(propertyEntity.getQuantity()));
        if(propertyEntity.getPricePromotion() > 0){
            detailRespone.setGiaBan(propertyEntity.getPricePromotion());
        }else {
            detailRespone.setGiaBan(propertyEntity.getPrice());
        }
        detailRespone.setGiaBanString(convertUtil.moneyToStringFormat(detailRespone.getGiaBan()));
        detailRespone.setTongTien(detailRespone.getGiaBan());
        detailRespone.setTongTienString(convertUtil.moneyToStringFormat(detailRespone.getGiaBan()));
        detailRespone.setStatusProduct(propertyEntity.getQuantity() == 0 ? "0" : "1");
        return detailRespone;
    }

    @Override
    public List<ProductPropertyRespone> findByRomAndColor(String romId, String colorId) {
        List<ProductPropertyRespone> respones = new ArrayList<>();
        List<ProductPropertyEntity> entityList = repo.findByRomAndColor(Long.valueOf(romId), Long.valueOf(colorId));
        for (ProductPropertyEntity a: entityList
        ) {
            respones.add(mapToDto(a));
        }
        return respones;
    }

    @Override
    public List<ProductPropertyRespone> findByColor(String colorId) {
        List<ProductPropertyRespone> respones = new ArrayList<>();
        List<ProductPropertyEntity> entityList = repo.findByColor(Long.valueOf(colorId));
        for (ProductPropertyEntity a: entityList
        ) {
            respones.add(mapToDto(a));
        }
        return respones;
    }

    @Override
    public ProductPropertyRespone updateProductProperty(ProductPropertyRequest request) {
        ProductPropertyEntity entity = repo.findByRomAndColor(Long.valueOf(request.getRomId()), Long.valueOf(request.getColorId())).get(0);
        entity.setPrice(Long.parseLong(request.getPrice()));
        entity.setQuantity(Long.parseLong(request.getQuantity()));
        ProductPropertyRespone respone = mapToDto(entity);
        try {
            repo.save(entity);
        }catch (Exception e){
            log.info(e.getMessage());
            return null;
        }
        return respone;
    }

    @Override
    public String udpateStatusProductProperty(ProductPropertyRequest request) {

        List<ProductPropertyEntity> entityList = repo.findByRomAndColor(Long.valueOf(request.getRomId()), Long.valueOf(request.getColorId()));
        if(entityList.size() < 1){
            log.error(String.valueOf(new WorldPhoneExp(ConstansErrorCode.ROM_NOT_EXIST).getErrorMessage().getVn()));
            return "false";
        }
        if(entityList.get(0).getPrice() == 0){
            return "false";
        }
        if(request.getStatus().equalsIgnoreCase("Ngừng kinh doanh")){
            entityList.get(0).setStatus(ConstansStatus.OFF);
        }else {
            entityList.get(0).setStatus(ConstansStatus.ON);
        }

        repo.save(entityList.get(0));
        return "ok";
    }

    @Override
    public boolean addImei(String romId, String colorId, List<String> imei) {
        try {
            List<ProductPropertyEntity> entityList = repo.findByRomAndColor(Long.valueOf(romId), Long.valueOf(colorId));
            for (ProductPropertyEntity a: entityList
            ) {
                long quantity = a.getQuantity();
                List<ImeiEntity> imeiEntityList = imeiRepo.findByDeleteFlagIsFalseAndPropertyProductId(a.getId());
                List<String> imeiValue = imeiEntityList.stream().map(ImeiEntity:: getValue).collect(Collectors.toList());
                imeiValue.addAll(imei);
                Set<String> setImei = new HashSet<>(imeiValue);
                int sizeImei = (int) (quantity - imeiEntityList.size());
                for (String ime: setImei
                ) {
                    int check  = 0;
                    for (ImeiEntity entity:imeiEntityList
                         ) {
                        if(ime.equals(entity.getValue())){
                            check = 1;
                            break;
                        }
                    }
                    if(sizeImei > 0 && check == 0){
                        ImeiEntity imeiEntity = new ImeiEntity();
                        imeiEntity.setValue(ime);
                        imeiEntity.setPropertyProductId(a.getId());
                        imeiEntity.setStatus(StatusImei.CHUA_BAN.getValue());
                        imeiRepo.save(imeiEntity);
                        sizeImei--;
                    }


                }
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }


    private ProductPropertyRespone mapToDto(ProductPropertyEntity entity){
        if(entity == null){
            return null;
        }else {
            Long imei = imeiRepo.countByPropertyProductId(entity.getId());
            ProductPropertyRespone dto = new ProductPropertyRespone();
            dto.setColorName(entity.getColorEntity().getValueColor());
            dto.setRomId(String.valueOf(entity.getRomEntity().getId()));
            dto.setColorId(String.valueOf(entity.getColorEntity().getId()));
            dto.setId(String.valueOf(entity.getId()));
            dto.setPrice(entity.getPrice());
            dto.setQuantity(entity.getQuantity());
            dto.setStatus(entity.getStatus());
            dto.setPriceString(convertUtil.moneyToStringFormat(entity.getPrice()));
            dto.setPricePromotion(entity.getPricePromotion());
            dto.setPricePromotionString(convertUtil.moneyToStringFormat(entity.getPricePromotion()));
            dto.setCountImei(imei == null ? 0L : imei);
            List<ImeiEntity> list = imeiRepo.findByDeleteFlagIsFalseAndPropertyProductId(entity.getId());
            dto.setImeiResponses(list.stream().map(this::mapToImei).collect(Collectors.toList()));
            return dto;
        }
    }

    private ImeiResponse mapToImei(ImeiEntity imeiEntity){
        return new ImeiResponse(String.valueOf(imeiEntity.getId()), imeiEntity.getValue());
    }
}
