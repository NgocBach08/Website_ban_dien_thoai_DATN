package com.example.testhtml.service.impl;

import com.example.testhtml.constant.ConstansErrorCode;
import com.example.testhtml.entity.ImageEntity;
import com.example.testhtml.entity.ProductEntity;
import com.example.testhtml.exception.WorldPhoneExp;
import com.example.testhtml.repo.ImageRepo;
import com.example.testhtml.repo.ProductRepo;
import com.example.testhtml.service.IImageService;
import com.example.testhtml.until.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements IImageService {
    private final ImageRepo imageRepo;
    private final SessionUtil sessionUtil;
    private final ProductRepo productRepo;

    @Override
    public String createImage(List<String> image, Long id) {
        ProductEntity productEntity = productRepo.findByIdAndDeleteFlagIsFalse(id);
        if(productEntity == null){
            throw new WorldPhoneExp(ConstansErrorCode.PRODUCT_NOT_EXIST);
        }
        for (int i = 1; i < image.size(); i++){
            ImageEntity entity = new ImageEntity();
            entity.setCreateBy("ADMIN");
            entity.setCreateDate(new Timestamp(System.currentTimeMillis()));
            entity.setModifierDate(new Timestamp(System.currentTimeMillis()));
            entity.setModifierBy((String) sessionUtil.getObject("username"));
            entity.setDeleteFlag(false);
            entity.setLing_image(image.get(i));
            entity.setProductEntity(productEntity);
            imageRepo.save(entity);
        }
        return "ok";
    }

    @Override
    public String editImage(List<String> image, Long id) {
        ProductEntity productEntity = productRepo.findByIdAndDeleteFlagIsFalse(id);
        if(productEntity == null){
            throw new WorldPhoneExp(ConstansErrorCode.PRODUCT_NOT_EXIST);
        }
        for (int i = 0; i < image.size(); i++){
            ImageEntity entity = new ImageEntity();
            entity.setCreateBy("ADMIN");
            entity.setCreateDate(new Timestamp(System.currentTimeMillis()));
            entity.setModifierDate(new Timestamp(System.currentTimeMillis()));
            entity.setModifierBy((String) sessionUtil.getObject("username"));
            entity.setDeleteFlag(false);
            entity.setLing_image(image.get(i));
            entity.setProductEntity(productEntity);
            imageRepo.save(entity);
        }
        return "ok";
    }
}
