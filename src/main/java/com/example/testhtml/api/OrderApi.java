package com.example.testhtml.api;

import com.example.testhtml.dto.request.order.OrderRequest;
import com.example.testhtml.dto.respone.order.OrderRespone;
import com.example.testhtml.service.IOrderDetailService;
import com.example.testhtml.service.IOrderService;
import com.example.testhtml.until.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderApi {
    private final IOrderService orderService;

    private final IOrderDetailService detailService;

    private final ConvertUtil convertUtil;

    @PostMapping("")
    public ResponseEntity<?> addOrder(@RequestBody OrderRequest request){
         String a = orderService.addOrder(request);
        if(a.equals("ok")){
            return ResponseEntity.ok().body(request);
        }else {
            return ResponseEntity.badRequest().body(a);
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getorder(@PathVariable("id") String id){
        OrderRespone respone = orderService.findByOrder(id);
        return ResponseEntity.ok().body(respone);
    }


    @PostMapping("edit-order-details")
    public ResponseEntity<?> editDetails(@RequestBody OrderRequest request){
        String respone = orderService.updateOrder(request);
        if(respone.equals("ok")){
            return ResponseEntity.ok().body(respone);
        }
        return ResponseEntity.badRequest().body(respone);
    }

    @PostMapping("confirm-order-sale-person")
    public ResponseEntity<?> confirmSaleOrders(@RequestBody OrderRequest request) throws ParseException {
//        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
//        DateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
//        Date date = formatter.parse(request.getDeliveryDate().toString());

//        if (convertUtil.strToDate(formatter1.format(formatter.parse(request.getDeliveryDate().toString())), "dd-MM-yyyy").compareTo(new Date()) < 0) {
//            throw new WorldPhoneExp(ConstansErrorCode.VOUCHER_DATE_NOT_PAST);
//        }
        String respone = orderService.confirmOrder(request);
        if(respone.equals("ok")){
            return ResponseEntity.ok().body(respone);
        }
        return ResponseEntity.badRequest().body(respone);
    }

    @PostMapping("confirm-export-order")
    public ResponseEntity<?> confirmExportOrders(@RequestBody OrderRequest request){
        String respone = orderService.exportOrder(request);
        if(respone.equals("ok")){
            return ResponseEntity.ok().body(respone);
        }
        return ResponseEntity.badRequest().body(respone);
    }

    @PostMapping("shipping")
    public ResponseEntity<?> confirmShipping(@RequestBody OrderRequest request){
        String respone = orderService.shippingOrder(request);
        if(respone.equals("ok")){
            return ResponseEntity.ok().body(respone);
        }
        return ResponseEntity.badRequest().body(respone);
    }


    @GetMapping("/{id}/{code}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id, @PathVariable("code") String codeOrder){
        String respone = detailService.deleteDetail(id, codeOrder);
        if(respone.equals("ok")){
            return ResponseEntity.ok().body(respone);
        }
        return ResponseEntity.badRequest().body(respone);
    }

    @GetMapping("pay-the-bill/{id}")
    public ResponseEntity<?> doneOrder(@PathVariable("id") String id){
        String respone = orderService.doneOrder(id);
        if(respone.equals("ok")){
            return ResponseEntity.ok().body(respone);
        }
        return ResponseEntity.badRequest().body(respone);
    }

    @GetMapping("/cancel-order/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable("id") String id){
        String respone = orderService.deleteOrder(String.valueOf(id));
        if(respone.equals("ok")){
            return ResponseEntity.ok().body(respone);
        }
        return ResponseEntity.badRequest().body(respone);
    }
}