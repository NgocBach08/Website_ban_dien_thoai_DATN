package com.example.testhtml.scheduing;

import com.example.testhtml.constant.ConstansStatus;
import com.example.testhtml.entity.*;
import com.example.testhtml.repo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 *
 * @author: hieu
 * @since: 31/07/2022
 * Project_name: com.example.testhtml.scheduing
 */
@Component
@Slf4j
public class PromotionJob {
    @Autowired
    private PromotionRepo promotionRepo;

    @Autowired
    private PromotionProductRepo promotionProductRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private RomRepo romRepo;

    @Autowired
    private PropertyProductRepo propertyProductRepo;

//    @Scheduled(cron = "${worldphone.schedule.promotion.cron}")
//    public void jobQuetPromotion(){
//        log.info("Start Job Promotion");
//        List<PromotionEnity> promotionEnityList = promotionRepo.findByDay();
//        if(promotionEnityList.size() > 0){
//            for (PromotionEnity promotion: promotionEnityList
//            ) {
//                List<PromotionProductEntity> promotionProductEntity = promotionProductRepo.findByPromotionIdAndDeleteFlagIsFalse(String.valueOf(promotion.getId()));
//                if(promotionProductEntity.size() == 0){
//                    List<ProductEntity> productEntities = productRepo.findAll();
//                    for (ProductEntity product: productEntities
//                    ) {
//                        changePrice(promotion, product);
//                    }
//
//                }else {
//                    for (PromotionProductEntity promotionProduct: promotionProductEntity
//                    ) {
//                        ProductEntity productEntity = productRepo.findByIdAndDeleteFlagIsFalse(Long.valueOf(promotionProduct.getProductId()));
//                        changePrice(promotion, productEntity);
//                    }
//                }
//            }
//        }else {
//            List<ProductPropertyEntity> productPropertyEntityList = propertyProductRepo.findByDeleteFlagIsFalse();
//            productPropertyEntityList.forEach(e -> {
//                e.setPricePromotion(0);
//                e.setPromotionId(0);
//            });
//            propertyProductRepo.saveAll(productPropertyEntityList);
//
//        }
//
//        log.info("End Job Promotion");
//    }

    private void changePrice(PromotionEnity promotion, ProductEntity productEntity) {
        List<RomEntity> romEntities = romRepo.findByProductEntity(productEntity.getId());
        for (RomEntity rom: romEntities
        ) {
            List<ProductPropertyEntity> productPropertyEntityList = propertyProductRepo.findByRom(rom.getId());
            for (ProductPropertyEntity propertyEntity: productPropertyEntityList
            ) {
                if(promotion.getTypeDiscount().equals(ConstansStatus.PERCENT) && propertyEntity.getPrice() > 0){
                    Long oldPrice = propertyEntity.getPrice();
                    Long khuyenMaiPrice = oldPrice / 100 * promotion.getDiscount();
                    if(oldPrice - khuyenMaiPrice > propertyEntity.getPricePromotion()){
                        propertyEntity.setPricePromotion(oldPrice - khuyenMaiPrice);
                        propertyEntity.setPromotionId(promotion.getId());
                        propertyProductRepo.save(propertyEntity);
                    }

                }
                if(promotion.getTypeDiscount().equals(ConstansStatus.MONEY) && propertyEntity.getPrice() > 0 && propertyEntity.getPrice() > promotion.getDiscount()){
                    Long oldPrice = propertyEntity.getPrice();
                    if(oldPrice - promotion.getDiscount() > propertyEntity.getPricePromotion()){
                        propertyEntity.setPricePromotion(oldPrice - promotion.getDiscount());
                        propertyEntity.setPromotionId(promotion.getId());
                        propertyProductRepo.save(propertyEntity);
                    }

                }
            }
        }
    }
}
