package com.example.testhtml.controller.attribute;

import com.example.testhtml.service.ILoaiOsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("loaios")
public class Loai_OsController {
    private final ILoaiOsService service;

    @GetMapping
    public String index(Model model){
        model.addAttribute("list", service.findAll());

        return "/views/product/attribute/loai_os";
    }
}
