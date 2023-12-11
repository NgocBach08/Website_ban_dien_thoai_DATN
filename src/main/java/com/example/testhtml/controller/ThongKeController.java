package com.example.testhtml.controller;

import com.example.testhtml.dto.respone.ThongKeDto;
import com.example.testhtml.repo.ThongKeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class ThongKeController {

    @Autowired
    private ThongKeRepo thongKeRepo;
    @GetMapping("/")
    public String index(Model model){
        List<ThongKeDto> thongKeDtos = thongKeRepo.findStockAkhirPerProductIn("null","12", "2023");
        model.addAttribute("test", "test");
        return "/views/index";
    }
}
