package com.example.testhtml.api;

import com.example.testhtml.dto.request.orderinvoice.OrderInvoiceRequest;
import com.example.testhtml.service.IOrderInvoiceService;
import com.example.testhtml.until.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceApi {
    private final IOrderInvoiceService orderInvoiceService;

    private final ConvertUtil convertUtil;

    @PostMapping("")
    public ResponseEntity<?> createOrderInvoice(@RequestBody OrderInvoiceRequest request){
        String check = orderInvoiceService.createNhap(request);
        if(check.equals("ok")){
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().body("Lỗi hệ thống");
    }

    @PutMapping("")
    public ResponseEntity<?> updateOrderInvoice(@RequestBody OrderInvoiceRequest request){
        String check = orderInvoiceService.updateNhap(request);
        if(check.equals("ok")){
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().body("Lỗi hệ thống");
    }
}
