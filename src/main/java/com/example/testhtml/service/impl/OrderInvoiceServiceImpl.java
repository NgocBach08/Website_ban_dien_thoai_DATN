package com.example.testhtml.service.impl;

import com.example.testhtml.common.StatusOrderInvoice;
import com.example.testhtml.common.StatusOrderInvoiceDetail;
import com.example.testhtml.constant.ConstansErrorCode;
import com.example.testhtml.dto.request.orderinvoice.OrderInvoiceRequest;
import com.example.testhtml.dto.request.orderinvoicedetail.OrderInvoiceDetailRequest;
import com.example.testhtml.dto.respone.order_invoice.OrderInvoiceRespone;
import com.example.testhtml.entity.InvoiceOrderDetailEntity;
import com.example.testhtml.entity.InvoiceOrderEntity;
import com.example.testhtml.entity.StaffEntity;
import com.example.testhtml.entity.SupplierEntity;
import com.example.testhtml.exception.WorldPhoneExp;
import com.example.testhtml.repo.InvoiceOrderDetailRepo;
import com.example.testhtml.repo.InvoiceOrderRepo;
import com.example.testhtml.repo.StaffRepo;
import com.example.testhtml.repo.SupplierRepo;
import com.example.testhtml.service.IOrderInvoiceDetailService;
import com.example.testhtml.service.IOrderInvoiceService;
import com.example.testhtml.until.ConvertUtil;
import com.example.testhtml.until.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderInvoiceServiceImpl implements IOrderInvoiceService {

    private final InvoiceOrderRepo orderRepo;

    private final InvoiceOrderDetailRepo detailRepo;

    private final SupplierRepo supplierRepo;

    private final SessionUtil sessionUtil;

    private final ConvertUtil convertUtil;

    private final StaffRepo staffRepo;

    private final IOrderInvoiceDetailService detailService;





    @Override
    public String createOrderInvoice(OrderInvoiceRequest request) {
        InvoiceOrderEntity entity = mapRequestToEntity(request);
        entity.setStatus(Integer.valueOf(request.getStatus()));
        entity.setTotalMoney(0L);
        entity = orderRepo.save(entity);
        if(entity != null){
            String checkDetail = detailService.createOrderInvoiceDetail(request.getDetailRequest(), entity);
            if(checkDetail.equals("ok")){
                return "ok";
            }

        }
        return "false";
    }

    @Override
    public String createNhap(OrderInvoiceRequest request) {
        if(request.getId().equals("0")){
            for (OrderInvoiceDetailRequest req: request.getDetailRequest()
            ) {
                if(req.getStatus().equals("")){

                }
            }
            InvoiceOrderEntity entity = new InvoiceOrderEntity();
            entity.setTotalMoney(request.getTotalMoney());
            entity.setNote(request.getNote());
            entity.setReceiveDate(new Date());
            entity.setGiamGia(request.getDiscount());
            entity.setTotalMoneyThua(request.getTienThua());
            entity.setTotalMoneyPay(request.getPaid());
            entity.setStatus(StatusOrderInvoice.DA_NHAN.getIndex());
            entity.setCodeOrder("HD00");
            Optional<SupplierEntity> supplierEntity = supplierRepo.findByIdAndDeleteFlagIsFalse(Long.valueOf(request.getSuppliderId()));
            if(!supplierEntity.isPresent()){
                log.error("Supplier not exist");
                throw new WorldPhoneExp(ConstansErrorCode.SUPPLIER_NOT_EXIST);
            }
            entity.setSupplierEntity(supplierEntity.get());
            StaffEntity staffEntity = staffRepo.findByEmailAndDeleteFlagIsFalse(String.valueOf(sessionUtil.getObject("username"))).get(0);
            entity.setStaffEntity(staffEntity);
            entity.setCreateBy("ADMIN");
            entity.setCreateDate(new Timestamp(System.currentTimeMillis()));
            entity.setModifierDate(new Timestamp(System.currentTimeMillis()));
            entity.setModifierBy("ADMIN");
            entity.setDeleteFlag(false);
            entity = orderRepo.save(entity);
            entity.setCodeOrder(entity.getCodeOrder() + entity.getId());
            orderRepo.save(entity);
            String check = detailService.createNhapDetail(request.getDetailRequest(), entity);
            if(!check.equals("ok")){
                return "false";
            }
            return "ok";
        }else {
            Optional<InvoiceOrderEntity> entity = orderRepo.findById(Long.valueOf(request.getId()));
            if(entity.isPresent()){
                Optional<SupplierEntity> supplierEntity = supplierRepo.findByIdAndDeleteFlagIsFalse(Long.valueOf(request.getSuppliderId()));
                if(!supplierEntity.isPresent()){
                    log.error("Supplier not exist");
                    throw new WorldPhoneExp(ConstansErrorCode.SUPPLIER_NOT_EXIST);
                }
                entity.get().setSupplierEntity(supplierEntity.get());
                entity.get().setTotalMoney(request.getTotalMoney());
                entity.get().setNote(request.getNote());
                entity.get().setReceiveDate(new Date());
                entity.get().setGiamGia(request.getDiscount());
                entity.get().setTotalMoneyThua(request.getTienThua());
                entity.get().setTotalMoneyPay(request.getPaid());
                entity.get().setStatus(StatusOrderInvoice.DA_NHAN.getIndex());
                StaffEntity staffEntity = staffRepo.findByEmailAndDeleteFlagIsFalse(String.valueOf(sessionUtil.getObject("username"))).get(0);
                entity.get().setStaffEntity(staffEntity);
                InvoiceOrderEntity e = orderRepo.save(entity.get());
                String check = detailService.createNhapDetail(request.getDetailRequest(), e);
                if(!check.equals("ok")){
                    return "false";
                }
                return "ok";
            }


        }
        return null;
    }

    @Override
    public String updateNhap(OrderInvoiceRequest request) {
        Optional<InvoiceOrderEntity> entity = orderRepo.findById(Long.valueOf(request.getId()));
        if(entity.isPresent()){
            Optional<SupplierEntity> supplierEntity = supplierRepo.findByIdAndDeleteFlagIsFalse(Long.valueOf(request.getSuppliderId()));
            if(!supplierEntity.isPresent()){
                log.error("Supplier not exist");
                throw new WorldPhoneExp(ConstansErrorCode.SUPPLIER_NOT_EXIST);
            }
            entity.get().setSupplierEntity(supplierEntity.get());
            entity.get().setTotalMoney(request.getTotalMoney());
            entity.get().setNote(request.getNote());
            entity.get().setGiamGia(request.getDiscount());
            entity.get().setTotalMoneyThua(request.getTienThua());
            entity.get().setTotalMoneyPay(request.getPaid());
            StaffEntity staffEntity = staffRepo.findByEmailAndDeleteFlagIsFalse(String.valueOf(sessionUtil.getObject("username"))).get(0);
            entity.get().setStaffEntity(staffEntity);
            InvoiceOrderEntity e = orderRepo.save(entity.get());
            String check = detailService.updateNhapDetail(request.getDetailRequest(), e);
            if(!check.equals("ok")){
                return "false";
            }
            return "ok";
        }
        return null;
    }

    @Override
    public String updateOrderInvoice(OrderInvoiceRequest request) {
        InvoiceOrderEntity entity = orderRepo.findByCodeOrder(request.getOrderCode());
        if(entity != null){
            InvoiceOrderEntity entity1 = mapRequestToEntity(request);
            entity1.setId(entity.getId());
            entity1.setStatus(entity.getStatus());
            orderRepo.save(entity1);
            String checkDetail = detailService.updateOrderInvoiceDetail(request.getDetailRequest(), entity);
            if(checkDetail.equals("ok")){
                return "ok";
            }

        }
        return null;
    }

    @Override
    public String findByCodeOrder(String code) {
        InvoiceOrderEntity entity = orderRepo.findByCodeOrder(code);
        if(entity != null){
            return "ok";
        }
        return "false";
    }

    @Override
    public List<OrderInvoiceRespone> finAll() {
        List<InvoiceOrderEntity> entityList = orderRepo.findByDeleteFlagIsFalseOrderByIdDesc();
        List<OrderInvoiceRespone> list = new ArrayList<>();
        for (InvoiceOrderEntity e: entityList
             ) {
            list.add(mapToRespone(e));
        }
        return list;
    }

    @Override
    public List<OrderInvoiceRespone> finAllNhapHang() {
        List<InvoiceOrderEntity> entityList = orderRepo.findAllNhapHang();
        List<OrderInvoiceRespone> list = new ArrayList<>();
        for (InvoiceOrderEntity e: entityList
        ) {
            list.add(mapToRespone(e));
        }
        return list;
    }

    @Override
    public List<OrderInvoiceRespone> finAllTotalMoney() {
        List<InvoiceOrderEntity> entityList = orderRepo.findByDeleteFlagIsFalseAndTotalMoney();
        List<OrderInvoiceRespone> list = new ArrayList<>();
        for (InvoiceOrderEntity e: entityList
        ) {
            list.add(mapToRespone(e));
        }
        return list;
    }

    @Override
    public String changeStatus(Long id, String message) {
        InvoiceOrderEntity entity = orderRepo.findByIdAndDeleteFlagIsFalse(id);
        if(message.equals("true")){
            if(entity.getStatus().equals(StatusOrderInvoice.DA_NHAN.getIndex()) || entity.getStatus().equals(StatusOrderInvoice.GIAO_THIEU.getIndex())){
                return "false";
            }
            entity.setStatus(StatusOrderInvoice.HUY.getIndex());
            orderRepo.save(entity);
        }else {
            entity.setStatus(StatusOrderInvoice.DA_DAT.getIndex());
            orderRepo.save(entity);
        }
        return "ok";
    }

    @Override
    public OrderInvoiceRespone findById(Long id) {
        InvoiceOrderEntity entity = orderRepo.findByIdAndDeleteFlagIsFalse(id);
        return mapToRespone(entity);
    }

    @Override
    public OrderInvoiceRespone duyetDon(Long id) {
        InvoiceOrderEntity entity = orderRepo.findByIdAndDeleteFlagIsFalse(id);
        if(entity.getSupplierEntity() == null){
            return null;
        }
        entity.setStatus(StatusOrderInvoice.DA_DAT.getIndex());
        entity = orderRepo.save(entity);
        List<InvoiceOrderDetailEntity> list = detailRepo.findByInvoiceOrderEntity(id);
        for (InvoiceOrderDetailEntity de:list
             ) {
            de.setStatus(String.valueOf(StatusOrderInvoiceDetail.DANG_NHAP.getIndex()));
        }
        detailRepo.saveAll(list);
        return mapToRespone(entity);
    }

    private OrderInvoiceRespone mapToRespone(InvoiceOrderEntity entity){
        OrderInvoiceRespone respone = new OrderInvoiceRespone();
        respone.setId(entity.getId());
        respone.setStaffName(entity.getStaffEntity().getFullName());
        respone.setTotalMoneny(entity.getTotalMoney());
        respone.setTotalMonenyString(convertUtil.moneyToStringFormat(entity.getTotalMoney()));
        respone.setGiamGia(entity.getGiamGia());
        respone.setGiamGiaString(convertUtil.moneyToStringFormat(entity.getGiamGia()));
        respone.setPhaiTraNCC(entity.getTotalMoneyPay());
        respone.setPhaiTraNCCString(convertUtil.moneyToStringFormat(entity.getTotalMoneyPay()));
        respone.setTienThua(entity.getTotalMoneyThua());
        respone.setTienThuaString(convertUtil.moneyToStringFormat(entity.getTotalMoneyThua()));
        respone.setCodeOrder(entity.getCodeOrder());
        respone.setSupplierName(entity.getSupplierEntity() != null ? entity.getSupplierEntity().getName() : "");
        respone.setNote(entity.getNote());
        respone.setReceiveDate(entity.getReceiveDate() != null ? String.valueOf(entity.getReceiveDate()).substring(0,10) : "");
        respone.setStatus(entity.getStatus());
        respone.setCreateDate(String.valueOf(entity.getCreateDate()));
        respone.setSupplierId(entity.getSupplierEntity() != null ? String.valueOf(entity.getSupplierEntity().getId()) : "");
        respone.setOrderDetail(detailService.findByOrderInvoice(entity.getId()));
        return respone;
    }


    private InvoiceOrderEntity mapRequestToEntity(OrderInvoiceRequest request){
        InvoiceOrderEntity entity = new InvoiceOrderEntity();
        entity.setCodeOrder(request.getOrderCode());
        if(request.getReceiveDate() != null){
            Date date = convertUtil.strToDate(request.getReceiveDate(), "dd-MM-yyyy");
            entity.setReceiveDate(date);
        }else {
            entity.setReceiveDate(null);
        }

        if(request.getSuppliderId() != null){
            Optional<SupplierEntity> supplierEntity = supplierRepo.findByIdAndDeleteFlagIsFalse(Long.valueOf(request.getSuppliderId()));
            if(!supplierEntity.isPresent()){
                log.error("Supplier not exist");
                throw new WorldPhoneExp(ConstansErrorCode.SUPPLIER_NOT_EXIST);
            }
            entity.setSupplierEntity(supplierEntity.get());
        }else {
            entity.setSupplierEntity(null);
        }

        if(request.getNote() != null){
            entity.setNote(request.getNote());
        }
        StaffEntity staffEntity = staffRepo.findByEmailAndDeleteFlagIsFalse(String.valueOf(sessionUtil.getObject("username"))).get(0);
        entity.setTotalMoneyPay(0L);
        entity.setGiamGia(0L);
        entity.setTotalMoneyThua(0L);
        entity.setTotalMoney(0L);
        entity.setStaffEntity(staffEntity);
        entity.setCreateBy("ADMIN");
        entity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        entity.setModifierDate(new Timestamp(System.currentTimeMillis()));
        entity.setModifierBy("ADMIN");
        entity.setDeleteFlag(false);
        return entity;
    }
}
