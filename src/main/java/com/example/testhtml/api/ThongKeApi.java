package com.example.testhtml.api;

import com.example.testhtml.dto.respone.ThongKeDto;
import com.example.testhtml.repo.ThongKeRepo;
import com.example.testhtml.until.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ThongKeApi {
    @Autowired
    private ThongKeRepo thongKeRepo;

    @Autowired
    private ConvertUtil convertUtil;

    @GetMapping("/top-product-sale/{day}/{month}/{year}")
    public List<ThongKeDto> thongKe(@PathVariable("day") String day,@PathVariable("month") String month, @PathVariable("year") String year){
        List<ThongKeDto> a =  thongKeRepo.findStockAkhirPerProductIn(day,month, year);
        Long tong = 0L;
        for (ThongKeDto b : a) {
            if (b.getPriceProduct() != null && b.getQuantityDaBan() != null) {
                tong += (Long.valueOf(b.getPriceProduct()) * Long.valueOf(b.getQuantityDaBan()));
            }
        }
        if (!a.isEmpty()) {
            a.get(0).setTotal(convertUtil.moneyToStringFormat(tong));
        }
        return a;
    }
}
