package com.example.testhtml.controller;

import com.example.testhtml.dto.respone.supplier.SupplierResponseDTO;
import com.example.testhtml.service.ISupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("supplier")
public class SupplierController {
        private final ISupplierService service;

        @GetMapping()
        public String index(Model model){
                List<SupplierResponseDTO> list = service.findAllByDeleteFlagFalse();
                model.addAttribute("list", list);
                return "/views/supplier/supplier";
        }



}
