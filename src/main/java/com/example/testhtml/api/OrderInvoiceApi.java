package com.example.testhtml.api;

import com.example.testhtml.constant.ConstansErrorCode;
import com.example.testhtml.dto.request.orderinvoice.OrderInvoiceRequest;
import com.example.testhtml.dto.respone.order_invoice.OrderInvoiceRespone;
import com.example.testhtml.exception.WorldPhoneExp;
import com.example.testhtml.service.IOrderInvoiceService;
import com.example.testhtml.until.ConvertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/order-invoice")
@RequiredArgsConstructor
@Slf4j
public class OrderInvoiceApi {
    private final IOrderInvoiceService orderInvoiceService;

    private final ConvertUtil convertUtil;


    @PostMapping("")
    public ResponseEntity<?> createOrderInvoice(@RequestBody OrderInvoiceRequest request){
        if (request.getReceiveDate() != null && convertUtil.strToDate(request.getReceiveDate(), "dd-MM-yyyy").compareTo(new Date()) < 0) {
            throw new WorldPhoneExp(ConstansErrorCode.VOUCHER_DATE_NOT_PAST);
        }
        if(request.getDetailRequest().size() == 0){
            throw new WorldPhoneExp(ConstansErrorCode.INVOICE_NOT_DETAIL);
        }
        log.info("create order invoice : {}", request);
        String check = orderInvoiceService.createOrderInvoice(request);
        if(check.equals("ok")){
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().body("Lỗi hệ thống");
    }

    @PutMapping("")
    public ResponseEntity<?> updateOrderInvoice(@RequestBody OrderInvoiceRequest request){
        if (convertUtil.strToDate(request.getReceiveDate(), "dd-MM-yyyy").compareTo(new Date()) < 0) {
            throw new WorldPhoneExp(ConstansErrorCode.VOUCHER_DATE_NOT_PAST);
        }
        if(request.getDetailRequest().size() == 0){
            throw new WorldPhoneExp(ConstansErrorCode.INVOICE_NOT_DETAIL);
        }
        log.info("create order invoice : {}", request);
        String check = orderInvoiceService.updateOrderInvoice(request);
        if(check.equals("ok")){
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().body("Lỗi hệ thống");
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> findByCode(@PathVariable("code") String code){
        String check = orderInvoiceService.findByCodeOrder(code);
        return ResponseEntity.ok().body(check);
    }

    @PutMapping("{id}/{message}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id, @PathVariable("message") String message){
        String check = orderInvoiceService.changeStatus(id, message);
        if(check.equals("ok")){
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().body("Đơn hàng đã nhận không thể ");
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        if(id == 0){
            return ResponseEntity.ok().body(null);
        }
        OrderInvoiceRespone respone = orderInvoiceService.findById(id);

        if(respone != null){
            return ResponseEntity.ok().body(respone);
        }
        return ResponseEntity.badRequest().body("false");
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> duyet(@PathVariable("id") Long id){
        OrderInvoiceRespone respone = orderInvoiceService.duyetDon(id);
        if(respone != null){
            return ResponseEntity.ok().body(respone);
        }
        return ResponseEntity.badRequest().body(new OrderInvoiceRequest());
    }

}
