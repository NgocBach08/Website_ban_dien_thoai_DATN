package com.example.testhtml.service.impl;

import com.example.testhtml.common.StatusImei;
import com.example.testhtml.common.StatusOrder;
import com.example.testhtml.dto.request.order.OrderRequest;
import com.example.testhtml.dto.request.orderdetail.OrderDetailRequest;
import com.example.testhtml.dto.respone.customer.CustomerRespone;
import com.example.testhtml.dto.respone.order.OrderRespone;
import com.example.testhtml.dto.respone.order_detail.OrderDetailRespone;
import com.example.testhtml.entity.*;
import com.example.testhtml.repo.*;
import com.example.testhtml.service.IOrderService;
import com.example.testhtml.until.ConvertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceimpl implements IOrderService {
    private final OrdersRepo ordersRepo;

    private final ConvertUtil convertUtil;

    private final CustomerRepo customerRepo;

    private final OrdersDetailRepo ordersDetailRepo;

    private final PropertyProductRepo propertyProductRepo;

    private final ProductRepo productRepo;

    private final VoucherRepo voucherRepo;


    private final ImeiRepo imeiRepo;
    @Override
    public List<OrderRespone> findAllOrder() {
        List<OrdersEntity> entities = ordersRepo.findByDeleteFlagIsFalseOrderByCreateDateDesc();
        List<OrderRespone> respones = new ArrayList<>();
        for (OrdersEntity entity: entities
             ) {
            OrderRespone orderRespone = new OrderRespone();
            orderRespone.setShipping(entity.getStatus());

            orderRespone.setOrderType(String.valueOf(entity.getTypeOrder()));
            orderRespone.setTotalString(convertUtil.moneyToStringFormat(entity.getTotalMoney()));
            orderRespone.setCodeOrder(entity.getCodeOrder());
            orderRespone.setTypeOrder(String.valueOf(entity.getStatusPay()));
            orderRespone.setOrderCreate(entity.getCreateDate().toString());
            orderRespone.setCustomerName(entity.getCustomerEntity().getFullName());
            respones.add(orderRespone);
        }
        return respones;
    }

    @Override
    public OrderRespone findByOrder(String id) {
        OrdersEntity entity = ordersRepo.findByCodeOrderAndDeleteFlagIsFalse(id);
        CustomerEntity customerEntity = customerRepo.getById(entity.getCustomerEntity().getId());
        List<OrdersDetailEntity> ordersDetailEntities = ordersDetailRepo.findByDeleteFlagIsFalseAndOrdersEntity(entity);
        List<OrderDetailRespone> orderDetailRespones = new ArrayList<>();
        Long tongTien = 0L;
        for (OrdersDetailEntity detail: ordersDetailEntities
             ) {
            OrderDetailRespone detailRespone = new OrderDetailRespone();
            detailRespone.setIdDetail(String.valueOf(detail.getId()));
            detailRespone.setIdProductProperty(String.valueOf(detail.getIdPropertyProduct()));
            ProductPropertyEntity propertyEntity = propertyProductRepo.getById(detail.getIdPropertyProduct());
            ProductEntity productEntity = propertyEntity.getRomEntity().getProductEntity();

            detailRespone.setQuantity(String.valueOf(detail.getQuantity()));
            detailRespone.setColorProduct(propertyEntity.getColorEntity().getValueColor());
            detailRespone.setRomProduct(propertyEntity.getRomEntity().getName());
            detailRespone.setGiaBan(detail.getPrice());
            detailRespone.setGiaBanString(convertUtil.moneyToStringFormat(detail.getPrice()));
            detailRespone.setNameProduct(productEntity.getName());
            detailRespone.setImageProduct(productEntity.getImage_key());
            detailRespone.setTonKho(String.valueOf(propertyEntity.getQuantity()));
            detailRespone.setTongTienString(convertUtil.moneyToStringFormat(detail.getPrice()* detail.getQuantity()) );
            detailRespone.setTongTien(detail.getPrice()* detail.getQuantity());
            tongTien+=detailRespone.getTongTien();
            detailRespone.setStatusProduct(propertyEntity.getQuantity() == 0 ? "0" : "1");
            orderDetailRespones.add(detailRespone);

        }
        CustomerRespone customerRespone = new CustomerRespone();
        customerRespone.setName(customerEntity.getFullName());
        customerRespone.setPhone(customerEntity.getPhoneNumber());

        OrderRespone respone = new OrderRespone();
        respone.setOrderType(String.valueOf(entity.getTypeOrder()));
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        respone.setDeliveryDate(df.format(entity.getReceiveDate()));
        respone.setShipping(entity.getStatus());
        respone.setTotalString(convertUtil.moneyToStringFormat(entity.getTotalMoney()));
        respone.setTotal(String.valueOf(entity.getTotalMoney()));
        respone.setCodeOrder(entity.getCodeOrder());
        respone.setAddress(entity.getAddress());
        respone.setTypeOrder(String.valueOf(entity.getStatusPay()));
        respone.setOrderCreate(entity.getCreateDate().toString());
        respone.setCustomerRespone(customerRespone);
        respone.setListDetail(orderDetailRespones);
        respone.setNote(entity.getNote());
        respone.setShipperPhone(entity.getPhoneShip());
        respone.setShipperName(entity.getNameShip());
       if(entity.getVoucherID() != null){
           VoucherEntity voucherEntity = voucherRepo.getByIdAndDeleteFlagIsFalse(String.valueOf(entity.getVoucherID()));
           respone.setVoucherName(voucherEntity.getName());
           if (voucherEntity.getTypeDiscount().equals("%")) {
               Long khuyenMai = tongTien / 100 * voucherEntity.getDiscount();
               respone.setVoucherGia(convertUtil.moneyToStringFormat(khuyenMai));
           }
       }
        return respone;
    }

    @Override
    public String addOrder(OrderRequest request) {
        CustomerEntity customerEntity = customerRepo.findByPhoneNumber(request.getPhoneNumber());

        VoucherEntity voucherEntity = null;

        if(customerEntity == null){
            customerEntity = new CustomerEntity();
            customerEntity.setPhoneNumber(request.getPhoneNumber());
            customerEntity.setFullName(request.getFullName());
            customerEntity = customerRepo.save(customerEntity);
        }

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        if(request.getVoucherId() != null && !request.getVoucherId().isEmpty()){
            voucherEntity = voucherRepo.getByEndDate(date,request.getVoucherId());
            if(voucherEntity == null){
                return "Voucher không tồn tại";
            }
            if (voucherEntity.getQuantity() == 0) {
                return "Voucher hết số lượng";
            }
        }
        OrdersEntity entity = new OrdersEntity();
        if(request.getOrderType().equals("COUNTER")){
            entity.setStatus(String.valueOf(StatusOrder.CHO_XUAT_HANG.getIndex()));
            entity.setTypeOrder(0);
        }else {
            entity.setStatus(String.valueOf(StatusOrder.CHO_XUAT_HANG.getIndex()));
            entity.setTypeOrder(1);
        }
        entity.setStatusPay(0);
        entity.setAddress(request.getRecipientAddress());
        entity.setCustomerEntity(customerEntity);
        entity.setReceiveDate(request.getDeliveryDate());
        entity = ordersRepo.save(entity);
        entity.setCodeOrder("HD000" + entity.getId());
        entity = ordersRepo.save(entity);
        long tong = 0;
        for (OrderDetailRequest detail: request.getDetailRequest()
             ) {
            OrdersDetailEntity detailEntity = new OrdersDetailEntity();
            detailEntity.setQuantity(Long.valueOf(detail.getQuantity()));
            detailEntity.setOrdersEntity(entity);
            detailEntity.setIdPropertyProduct(Long.valueOf(detail.getProductId().replace("productDetail", "")));
            ProductPropertyEntity propertyEntity = propertyProductRepo.getById(Long.valueOf(detail.getProductId().replace("productDetail", "")));
            if(propertyEntity.getPricePromotion() > 0){
                detailEntity.setPrice(propertyEntity.getPricePromotion());
                tong+=propertyEntity.getPricePromotion();
            }else {
                detailEntity.setPrice(propertyEntity.getPrice());
                tong+=propertyEntity.getPrice();
            }
            //propertyEntity.setQuantity(propertyEntity.getQuantity() - Long.parseLong(detail.getQuantity()));
            //propertyProductRepo.save(propertyEntity);
            ordersDetailRepo.save(detailEntity);
        }

        if(request.getVoucherId() != null && !request.getVoucherId().isEmpty()) {
            voucherEntity = voucherRepo.getByEndDate(date,request.getVoucherId());
            voucherEntity.setQuantity(voucherEntity.getQuantity() - 1);
            if(voucherEntity != null){
                entity.setVoucherID(Long.valueOf(voucherEntity.getId()));
                if(voucherEntity.getTypeDiscount().equals("đ")){
                    tong = tong - voucherEntity.getDiscount();
                }else {
                    tong = tong - (tong / 100) * voucherEntity.getDiscount();
                }
            }
            voucherRepo.save(voucherEntity);
        }
        entity.setTotalMoney(tong);
        ordersRepo.save(entity);
        return "ok";
    }

    private void addOrderVoucher(CustomerEntity entity, OrderRequest request){
        long tong = 0;
        for (OrderDetailRequest detail: request.getDetailRequest()
             ) {
            ProductPropertyEntity propertyEntity = propertyProductRepo.getById(Long.valueOf(detail.getId()));
            tong += propertyEntity.getPricePromotion() == 0 ? propertyEntity.getPrice() * Long.parseLong(detail.getQuantity()) : propertyEntity.getPricePromotion() * Long.parseLong(detail.getQuantity());
        }
        OrdersEntity entityOrder = new OrdersEntity();

    }

    @Override
    public String updateOrder(OrderRequest request) {
        OrdersEntity entity = ordersRepo.findByCodeOrderAndDeleteFlagIsFalse(request.getId());
        entity.setTotalMoney(Long.valueOf(request.getTotalMoney()));
        ordersRepo.save(entity);
        for (OrdersDetailEntity entityDetail : entity.getOrdersDetailEntities()){
            entityDetail.setDeleteFlag(true);
            ordersDetailRepo.save(entityDetail);
        }
        for (OrderDetailRequest detail : request.getDetailRequest()
             ) {

            if(detail.getId() != null && !detail.getId().equals("")){
                String id = detail.getId().replace("orderDetail", "");
                OrdersDetailEntity detailEntity = ordersDetailRepo.findByDeleteFlagIsTrueAndId(Long.valueOf(id));
                detailEntity.setQuantity(Long.valueOf(detail.getQuantity()));
                detailEntity.setPrice(Long.valueOf(detail.getPrice()));
                detailEntity.setDeleteFlag(false);
                ordersDetailRepo.save(detailEntity);
            }else {
                OrdersDetailEntity detailEntity = new OrdersDetailEntity();
                detailEntity.setOrdersEntity(entity);
                detailEntity.setIdPropertyProduct(Long.valueOf(detail.getProductId().replace("productDetail", "")));
                detailEntity.setQuantity(Long.valueOf(detail.getQuantity()));
                detailEntity.setPrice(Long.valueOf(detail.getPrice()));
                detailEntity.setDeleteFlag(false);
                ordersDetailRepo.save(detailEntity);
            }
        }

        return "ok";
    }

    @Override
    public String confirmOrder(OrderRequest request) throws ParseException {
        OrdersEntity entity = ordersRepo.findByCodeOrderAndDeleteFlagIsFalse(request.getId());
        entity.setAddress(request.getRecipientAddress());
        entity.setReceiveDate(request.getDeliveryDate());
        entity.setStatus(String.valueOf(StatusOrder.CHO_XUAT_HANG.getIndex()));
        List<OrdersDetailEntity> list = ordersDetailRepo.findByDeleteFlagIsFalseAndOrdersEntity(entity);
        for (OrdersDetailEntity detail: list
        ) {
            ProductPropertyEntity propertyEntity = propertyProductRepo.findById(detail.getIdPropertyProduct()).get();
            if(propertyEntity.getQuantity() - detail.getQuantity() < 0){
                return "Sản phẩm đang hết hàng";
            }else {
                //propertyEntity.setQuantity(propertyEntity.getQuantity() - detail.getQuantity());
//                if(propertyEntity.getQuantity() == 0){
//                    propertyEntity.setStatus("OFF");
//                }
//                propertyProductRepo.save(propertyEntity);
            }
        }
        ordersRepo.save(entity);
        return "ok";
    }

    @Override
    public String shippingOrder(OrderRequest request) {
        OrdersEntity entity = ordersRepo.findByCodeOrderAndDeleteFlagIsFalse(request.getId());
        entity.setNameShip(request.getShipperName());
        entity.setNoteShip(request.getNote());
        entity.setStatus(String.valueOf(StatusOrder.DANG_GIAO_HANG.getIndex()));
        entity.setPhoneShip(request.getShipperPhone());
        ordersRepo.save(entity);
        return "ok";
    }

    @Override
    public String exportOrder(OrderRequest request) {
        OrdersEntity entity = ordersRepo.findByCodeOrderAndDeleteFlagIsFalse(request.getId());
        List<OrdersDetailEntity> list = ordersDetailRepo.findByDeleteFlagIsFalseAndOrdersEntity(entity);
        if(list == null || list.size() == 0){
            return "Hoá đơn chưa có sản phẩm nào";
        }
        for (OrdersDetailEntity detail: list
        ) {
            ProductPropertyEntity propertyEntity = propertyProductRepo.findById(detail.getIdPropertyProduct()).get();
            if(propertyEntity.getQuantity() - detail.getQuantity() < 0){
                return "Sản phẩm đang hết hàng";
            }else {
                propertyEntity.setQuantity(propertyEntity.getQuantity() - detail.getQuantity());
                if(propertyEntity.getQuantity() == 0){
                    propertyEntity.setStatus("OFF");
                }
                propertyProductRepo.save(propertyEntity);
            }
        }
        if(entity.getTypeOrder() == 0){
            entity.setStatus(String.valueOf(StatusOrder.HOAN_THANH.getIndex()));
            ordersRepo.save(entity);
            return "ok";
        }

        entity.setAddress(request.getRecipientAddress());
        entity.setReceiveDate(request.getDeliveryDate());
        entity.setStatus(String.valueOf(StatusOrder.CHO_GIAO_HANG.getIndex()));
        ordersRepo.save(entity);
        return "ok";
    }

    @Override
    public String doneOrder(String  id) {
        OrdersEntity entity = ordersRepo.findByCodeOrderAndDeleteFlagIsFalse(id);
        entity.setStatus(String.valueOf(StatusOrder.HOAN_THANH.getIndex()));
        ordersRepo.save(entity);
        return "ok";
    }

    @Override
    public String deleteOrder(String request) {
        OrdersEntity ordersEntity = ordersRepo.findByCodeOrderAndDeleteFlagIsFalse(request);
        if (ordersEntity.getVoucherID() != null) {
            VoucherEntity voucherEntity = voucherRepo.findById(String.valueOf(ordersEntity.getVoucherID())).get();
            voucherEntity.setQuantity(voucherEntity.getQuantity() + 1);
            voucherRepo.save(voucherEntity);
        }
        ordersEntity.setStatus(String.valueOf(StatusOrder.HUY.getIndex()));
        ordersRepo.save(ordersEntity);
        List<OrdersDetailEntity> list = ordersDetailRepo.findByDeleteFlagIsFalseAndOrdersEntity(ordersEntity);

        for (OrdersDetailEntity detail: list
             ) {
            ProductPropertyEntity propertyEntity = propertyProductRepo.getById(detail.getIdPropertyProduct());
            propertyEntity.setQuantity(detail.getQuantity() + propertyEntity.getQuantity());
            propertyProductRepo.save(propertyEntity);
            List<ImeiEntity> imeiEntityList = imeiRepo.findByDeleteFlagIsFalseAndPropertyProductIdAndOrderDetailId(propertyEntity.getId(), detail.getId());
            for (ImeiEntity entity : imeiEntityList){
                entity.setStatus(StatusImei.CHUA_BAN.getValue());
                entity.setOrderDetailId(null);
                imeiRepo.save(entity);

            }
        }
        return "ok";
    }
}
