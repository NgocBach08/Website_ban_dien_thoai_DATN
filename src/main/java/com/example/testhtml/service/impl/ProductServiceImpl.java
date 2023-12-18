package com.example.testhtml.service.impl;

import com.example.testhtml.common.StatusProduct;
import com.example.testhtml.constant.ConstansErrorCode;
import com.example.testhtml.dto.request.product.ProductRequestAdd;
import com.example.testhtml.dto.request.product.ProductRequestEdit;
import com.example.testhtml.dto.respone.attribute.AttributeRespone;
import com.example.testhtml.dto.respone.category.CategoryResponeDto;
import com.example.testhtml.dto.respone.image.ImageRespone;
import com.example.testhtml.dto.respone.imei.ImeiResponse;
import com.example.testhtml.dto.respone.product.ProductPropertyRespone;
import com.example.testhtml.dto.respone.product.ProductResponse;
import com.example.testhtml.dto.respone.rom.RomRespone;
import com.example.testhtml.entity.*;
import com.example.testhtml.exception.WorldPhoneExp;
import com.example.testhtml.repo.ImageRepo;
import com.example.testhtml.repo.ImeiRepo;
import com.example.testhtml.repo.ProductRepo;
import com.example.testhtml.repo.PropertyProductRepo;
import com.example.testhtml.service.*;
import com.example.testhtml.until.ConvertUtil;
import com.example.testhtml.until.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final ProductRepo productRepo;

    private final PropertyProductRepo propertyProductRepo;
    private final AttributeProductService attributeProductService;
    private final IRomService romService;
    private final ImageRepo imageRepo;
    private final ICategoryService categoryService;
    private final SessionUtil sessionUtil;
    private final IImageService iImageService;

    private final ConvertUtil convertUtil;

    private final ImeiRepo imeiRepo;

    private ModelMapper modelMapper = new ModelMapper();




    @Override
    public List<ProductResponse> findAll() {
        List<ProductEntity> productEntityList = productRepo.findAll();
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (ProductEntity a : productEntityList){

            productResponseList.add(mapToRespone(a));
        }
        return productResponseList;
    }

    @Override
    public String createProduct(ProductRequestAdd requestProduct) {
        if(requestProduct.getRomRequestAdds().size() == 0){
            log.error(String.valueOf(new WorldPhoneExp(ConstansErrorCode.ROM_NOT_EXIST).getErrorMessage().getVn()));
            return String.valueOf(new WorldPhoneExp(ConstansErrorCode.ROM_NOT_EXIST).getErrorMessage().getVn());
        }
        ProductEntity entity = mapToRequest(requestProduct);


        entity.setStatus(StatusProduct.NGUNG_KINH_DOANH.getStatus());
        entity = productRepo.save(entity);
        if(!attributeProductService.saveAttribute(requestProduct.getAttributeRequestAdd(), entity.getId()).equals("ok")){
            entity.setDeleteFlag(true);
            entity = productRepo.save(entity);
            log.error("them moi san pham that bai");
            return "that bai";
        }
        if(requestProduct.getImage().size() > 1){
            if(!iImageService.createImage(requestProduct.getImage(), entity.getId()).equals("ok")){
                entity.setDeleteFlag(true);
                entity = productRepo.save(entity);
                log.error("them moi san pham that bai");
                return "that bai";
            }
        }

        if(!romService.createRom(requestProduct.getRomRequestAdds(), entity).equals("ok")){
            entity.setDeleteFlag(true);
            entity = productRepo.save(entity);
            log.error("them moi san pham that bai");
            return "that bai";
        }

        return "ok";

    }

    @Override
    public String editProduct(ProductRequestEdit requestEdit) {
        if(requestEdit.getRomRequestAdds().size() == 0){
            throw new WorldPhoneExp(ConstansErrorCode.ROM_NOT_EXIST);
        }
        if(requestEdit.getIdProduct() == null){
            throw new WorldPhoneExp(ConstansErrorCode.PRODUCT_NOT_EXIST);
        }

        ProductEntity entity = productRepo.findByIdAndDeleteFlagIsFalse(requestEdit.getIdProduct());
        CategoryEntity categoryEntity = categoryService.findById(String.valueOf(requestEdit.getCategoryId()));
        if(categoryEntity == null){
            throw new WorldPhoneExp(ConstansErrorCode.CATEGORY_NOT_EXIST);
        }
        entity.setCategory(categoryEntity);
        if(!attributeProductService.updateAttribute(requestEdit.getAttributeRequestedit() , entity.getId()).equals("ok")){
            log.error("update sản phẩm thất bại ở phần attribute");
            return "false";
        }
        if(requestEdit.getImage() != null){
            if(requestEdit.getImage().size() > 0){
                if(!iImageService.editImage(requestEdit.getImage(), entity.getId()).equals("ok")){
                    entity.setDeleteFlag(true);
                    entity = productRepo.save(entity);
                    log.error("them moi san pham that bai");
                    return "that bai";
                }
            }
        }

        if(!romService.createRomWithProductEdit(requestEdit.getRomRequestAdds(), entity).equals("ok")){
            entity.setDeleteFlag(true);
            log.error("update sản phẩm thất bại ở phần rom");
            return "false";
        }
        entity.setName(requestEdit.getNameProduct());
        entity.setModifierDate(new Timestamp(System.currentTimeMillis()));
        entity.setModifierBy((String) sessionUtil.getObject("username"));
        productRepo.save(entity);
        return "ok";
    }

    @Override
    public ProductResponse getProduct(Long id)  {
        ProductEntity entity = productRepo.findByIdAndDeleteFlagIsFalse(id);
        if(entity == null){
            throw new WorldPhoneExp(ConstansErrorCode.PRODUCT_NOT_EXIST);
        }
        ProductResponse response = mapToRespone(entity);
        List<RomEntity> romEntityList = entity.getRomEntities();
        List<RomRespone> romRespones = new ArrayList<>();
        for (RomEntity r: romEntityList
        ) {
            RomRespone romRespone = new RomRespone();
            romRespone.setName(r.getName());
            romRespone.setId(String.valueOf(r.getId()));
            List<ProductPropertyRespone> productPropertyResponeList = new ArrayList<>();
            for (ProductPropertyEntity p: r.getProductProperties()
            ) {
                ProductPropertyRespone productPropertyRespone = new ProductPropertyRespone();
                Long imei = imeiRepo.countByPropertyProductId(p.getId());
                Long imeiTrenDon = imeiRepo.countByPropertyProductIdTrenDon(p.getId());
                productPropertyRespone.setCountImei(imei == null ? 0L : imei);
                productPropertyRespone.setCountImeiTrenDon(imeiTrenDon == null ? 0L : imeiTrenDon);
                productPropertyRespone.setQuantity(p.getQuantity());
                productPropertyRespone.setId(String.valueOf(p.getId()));
                productPropertyRespone.setRomId(String.valueOf(p.getRomEntity().getId()));
                productPropertyRespone.setColorId(String.valueOf(p.getColorEntity().getId()));
                productPropertyRespone.setPrice(p.getPrice());
                productPropertyRespone.setStatus(p.getStatus());
                productPropertyRespone.setPriceString(convertUtil.moneyToStringFormat(p.getPrice()));
                productPropertyRespone.setColorName(p.getColorEntity().getValueColor());
                List<ImeiEntity> list = imeiRepo.findByDeleteFlagIsFalseAndPropertyProductId(entity.getId());
                productPropertyRespone.setImeiResponses(list.stream().map(this::mapToImei).collect(Collectors.toList()));
                productPropertyResponeList.add(productPropertyRespone);
            }
            romRespone.setProductPropertyResponeList(productPropertyResponeList);
            romRespones.add(romRespone);
        }
        response.setRomRespones(romRespones);
        return response;
    }


    @Override
    public List<ProductResponse> getName(String name) {
        List<ProductEntity> entities = productRepo.findByName(name);
        if(entities.size() == 0){
            throw new WorldPhoneExp(ConstansErrorCode.PRODUCT_NOT_EXIST);
        }

        List<ProductResponse> list = new ArrayList<>();
        for (ProductEntity e: entities
             ) {
            int check = 0;
            ProductResponse response = mapToRespone(e);
            List<RomEntity> romEntityList = e.getRomEntities();
            List<RomRespone> romRespones = new ArrayList<>();
            for (RomEntity r: romEntityList
            ) {
                RomRespone romRespone = new RomRespone();
                romRespone.setName(r.getName());
                romRespone.setId(String.valueOf(r.getId()));
                List<ProductPropertyRespone> productPropertyResponeList = new ArrayList<>();
                List<ProductPropertyEntity> productPropertyEntityList = propertyProductRepo.findByRomAAndStatus(r.getId());
                if(productPropertyEntityList != null && productPropertyEntityList.size() > 0 ){
                    for (ProductPropertyEntity p: productPropertyEntityList
                    ) {
                        if(p.getQuantity()  > 0){
                            Long imei = imeiRepo.countByPropertyProductId(p.getId());
                            Long imeiTrenDon = imeiRepo.countByPropertyProductIdTrenDon(p.getId());
                            ProductPropertyRespone productPropertyRespone = new ProductPropertyRespone();
                            productPropertyRespone.setQuantity(p.getQuantity());
                            productPropertyRespone.setId(String.valueOf(p.getId()));
                            productPropertyRespone.setPrice(p.getPrice());
                            productPropertyRespone.setPriceString(convertUtil.moneyToStringFormat(p.getPrice()));
                            productPropertyRespone.setColorName(p.getColorEntity().getValueColor());
                            productPropertyRespone.setStatus(p.getStatus());
                            List<ImeiEntity> list1 = imeiRepo.findByDeleteFlagIsFalseAndPropertyProductId(p.getId());
                            productPropertyRespone.setImeiResponses(list1.stream().map(this::mapToImei).collect(Collectors.toList()));
                            productPropertyRespone.setCountImei(imei == null ? 0L : imei);
                            productPropertyRespone.setCountImeiTrenDon(imeiTrenDon == null ? 0L : imeiTrenDon);
                            if(list1.size() > 0){
                                check = 1;
                                productPropertyResponeList.add(productPropertyRespone);
                            }
                        }

                    }
                    romRespone.setProductPropertyResponeList(productPropertyResponeList);
                    romRespones.add(romRespone);
                }

            }
            if(check == 1){
                response.setRomRespones(romRespones);
                list.add(response);
            }

        }

        return list;

    }

    @Override
    public List<ProductResponse> getNameNhapHang(String name) {
        List<ProductEntity> entities = productRepo.findByName(name);
        if(entities.size() == 0){
            throw new WorldPhoneExp(ConstansErrorCode.PRODUCT_NOT_EXIST);
        }

        List<ProductResponse> list = new ArrayList<>();
        for (ProductEntity e: entities
        ) {
            ProductResponse response = mapToRespone(e);
            List<RomEntity> romEntityList = e.getRomEntities();
            List<RomRespone> romRespones = new ArrayList<>();
            for (RomEntity r: romEntityList
            ) {
                RomRespone romRespone = new RomRespone();
                romRespone.setName(r.getName());
                romRespone.setId(String.valueOf(r.getId()));
                List<ProductPropertyRespone> productPropertyResponeList = new ArrayList<>();
                List<ProductPropertyEntity> productPropertyEntityList = propertyProductRepo.findByRomAAndStatus(r.getId());

                romRespone.setProductPropertyResponeList(productPropertyResponeList);
                romRespones.add(romRespone);


            }
                response.setRomRespones(romRespones);
                list.add(response);

        }

        return list;
    }

    @Override
    public String deleteProduct(Long id) {
        ProductEntity entity = productRepo.findByIdAndDeleteFlagIsFalse(id);
        if(entity == null){
            return String.valueOf(new WorldPhoneExp(ConstansErrorCode.PRODUCT_NOT_EXIST).getErrorMessage().getVn());
        }
        entity.setDeleteFlag(true);
        productRepo.save(entity);
        return "ok";
    }

    @Override
    public String editStatusProduct(Long id, String value) {
        ProductEntity entity = productRepo.findByIdAndDeleteFlagIsFalse(id);
        if(entity == null){
            return String.valueOf(new WorldPhoneExp(ConstansErrorCode.PRODUCT_NOT_EXIST).getErrorMessage().getVn());
        }
        entity.setStatus(value);
        productRepo.save(entity);
        return "ok";
    }


    // map tu entity ve dto
    private ProductResponse mapToRespone(ProductEntity x){
        if(x == null){
            throw new WorldPhoneExp(ConstansErrorCode.PRODUCT_NOT_EXIST);
        }else {
            ProductResponse response = new ProductResponse();
            response.setId(x.getId());
            response.setNote(x.getNote());
            response.setImageProduct(x.getImage_key());
            response.setCreate_By(x.getCreateBy());
            response.setCreate_Date(new Date(x.getCreateDate().getTime()));
            response.setNameProduct(x.getName());
            response.setStatus(x.getStatus());

            // tìm category
            CategoryEntity categoryEntity = x.getCategory();
            CategoryResponeDto categoryResponeDto = new CategoryResponeDto();
            categoryResponeDto.setCategoryId(String.valueOf(categoryEntity.getId()));
            categoryResponeDto.setCategoryName(categoryEntity.getName());
            response.setCategoryResponeDto(categoryResponeDto);

            // tìm emei


            List<ImageEntity> imageEntities = imageRepo.findByProductEntity(response.getId());
            List<ImageRespone> imageProduct = new ArrayList<>();
            for (ImageEntity a: imageEntities
                 ) {
                ImageRespone imageRespone = new ImageRespone();
                imageRespone.setImg_link(a.getLing_image());
                imageRespone.setId(String.valueOf(a.getId()));
                imageProduct.add(imageRespone);
            }
            response.setImage(imageProduct);
            //response.setAttributeRespone(attributeService.findByProductId(response.getId()));
            response.setRomRespones(romService.findByProduct(x.getId()));

            // tìm attribute
            AttributeRespone attributeRespone = attributeProductService.findByProduct(x.getId());
            response.setAttributeRespone(attributeRespone);
            return response;
        }
    }

    //map to dto ve entity
    private ProductEntity mapToRequest(ProductRequestAdd x){
        if(x == null){
            throw new WorldPhoneExp(ConstansErrorCode.PRODUCT_NOT_EXIST);
        }
        ProductEntity entity = new ProductEntity();
        entity.setNote(x.getNote());
        entity.setImage_key(x.getImage().get(0));
        CategoryEntity categoryEntity = categoryService.findById(String.valueOf(x.getCategoryId()));
        if(categoryEntity == null){
            throw new WorldPhoneExp(ConstansErrorCode.CATEGORY_NOT_EXIST);
        }
        entity.setCategory(categoryEntity);
        entity.setName(x.getNameProduct());
        entity.setStatus("ON");
        entity.setCreateBy("ADMIN");
        entity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        entity.setModifierDate(new Timestamp(System.currentTimeMillis()));
        entity.setModifierBy((String) sessionUtil.getObject("username"));
        entity.setDeleteFlag(false);
        return entity;
    }


    private ImeiResponse mapToImei(ImeiEntity imeiEntity){
        return new ImeiResponse(String.valueOf(imeiEntity.getId()), imeiEntity.getValue());
    }








}
