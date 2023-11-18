package com.example.testhtml.api;

import com.example.testhtml.dto.respone.ThongKeDto;
import com.example.testhtml.repo.ThongKeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ThongKeApi {
    @Autowired
    private ThongKeRepo thongKeRepo;

    @GetMapping("/top-product-sale/{month}/{year}")
    public List<ThongKeDto> thongKe(@PathVariable("month") Integer month, @PathVariable("year") Integer year){
        return thongKeRepo.findStockAkhirPerProductIn(month, year);
    }
}
