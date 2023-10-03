package com.example.testhtml.api;


import com.example.testhtml.dto.request.imei.ExcelRequest;
import com.example.testhtml.dto.request.orderinvoice.OrderInvoiceRequest;
import com.example.testhtml.dto.request.product_property.ProductPropertyRequest;
import com.example.testhtml.dto.respone.order_detail.OrderDetailRespone;
import com.example.testhtml.dto.respone.product.ProductPropertyRespone;
import com.example.testhtml.service.IProductPropertyService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-property")
public class ProductPropertyApi {

    private final IProductPropertyService service;


    @GetMapping("{id}")
    public ResponseEntity<?> findByID(@PathVariable("id") Long id){
        OrderDetailRespone respone = service.findById(id);
        return ResponseEntity.ok().body(respone);
    }



    @RequestMapping(value = "/{romId}/{colorId}" , method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<?> updatImei(@PathVariable("romId") Long romId
            , @PathVariable("colorId") Long colorId
            , @ModelAttribute ExcelRequest reapExcelDataFile) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getFormData().getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        List<String> list = new ArrayList<>();
        for(int i=0;i<worksheet.getPhysicalNumberOfRows() ;i++) {
            XSSFRow row = worksheet.getRow(i);
            if(list.size() > 0){
                boolean check = true;
                for (String a : list){
                    if(a.equals(row.getCell(0).getRawValue())){
                        check = false;
                        break;
                    }
                }
                if(check){
                    list.add(row.getCell(0).getRawValue());
                }
            }else {
                list.add(row.getCell(0).getRawValue());
            }
        }
        if(service.addImei(String.valueOf(romId), String.valueOf(colorId), list)){
            return ResponseEntity.ok().body(new OrderInvoiceRequest());
        }

        return ResponseEntity.badRequest().body(new OrderInvoiceRequest());
    }

    @PostMapping("")
    public ResponseEntity<?> getProductProperty(@RequestBody ProductPropertyRequest request){
        List<ProductPropertyRespone> list = new ArrayList<>();
        if(request.getColorId() == null){
            list = service.findByRom(Long.valueOf(request.getRomId()));
            if(list.size() > 0){
                return ResponseEntity.ok().body(list);
            }
        }else {
            list = service.findByRomAndColor(request.getRomId(), request.getColorId());
            if(list.size() > 0){
                return ResponseEntity.ok().body(list.get(0));
            }
        }

        return ResponseEntity.ok().body("false");
    }

    @PutMapping("")
    public ResponseEntity<?> udpateProductProperty(@RequestBody ProductPropertyRequest request){
        ProductPropertyRespone respone = service.updateProductProperty(request);
        if(respone != null){
            return ResponseEntity.ok().body(respone);
        }
        return ResponseEntity.badRequest().body("false");
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateProductPropertyStatus(@RequestBody ProductPropertyRequest request){
        String check = service.udpateStatusProductProperty(request);
        if(check.equals("ok")){
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().body("false");
    }

}
