package com.example.testhtml.controller.attribute;

import com.example.testhtml.service.ILoaiOsService;
import com.example.testhtml.service.IOsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("os")
public class OsController {
    private final IOsService service;
    private final ILoaiOsService service1;



    @GetMapping
    public String index(Model model){
        model.addAttribute("list", service.findAll());
        model.addAttribute("listos", service1.findAll());
        System.out.println(service1.findAll().get(0));
        return "/views/product/attribute/os";
    }
}
