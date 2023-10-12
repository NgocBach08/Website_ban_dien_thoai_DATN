package com.example.testhtml.api;


import com.example.testhtml.dto.respone.category.CategoryDTO;
import com.example.testhtml.dto.respone.category.CategoryResponeDto;
import com.example.testhtml.service.ICateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cate")
public class CateApi {

    @Autowired
    private ICateService service;

    @GetMapping("/{id}")
    public CategoryResponeDto findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> addScreen(@RequestBody CategoryDTO request) {
        String status = service.save(request);
        if (status.equalsIgnoreCase("ok")) {
            return ResponseEntity.ok().body(request);
        }
        return ResponseEntity.badRequest().body(request);
    }


}
