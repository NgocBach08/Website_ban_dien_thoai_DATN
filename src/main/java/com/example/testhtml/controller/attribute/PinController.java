package com.example.testhtml.controller.attribute;

import com.example.testhtml.dto.respone.attribute.pin.PinRespone;
import com.example.testhtml.service.IPinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PinController {

    private final IPinService service;

    @GetMapping("pin")
    public String index(Model model){
        List<PinRespone> list = service.findAll();
        model.addAttribute("list", list);
        return "/views/product/attribute/pin";
    }
}
