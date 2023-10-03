package com.example.testhtml.api.attribute;


import com.example.testhtml.dto.request.attribute.loai_screen.Loai_ScreenRequest;
import com.example.testhtml.dto.respone.attribute.loai_screen.Loai_ScreenRespone;
import com.example.testhtml.service.ILoaiScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/loaiscreen")

public class Loai_ScreenApi {

        @Autowired
        private ILoaiScreenService service;

        @GetMapping("/{id}")
        public Loai_ScreenRespone findById(@PathVariable("id") String id) {
            return service.findById(id);
        }

        @PostMapping
        public ResponseEntity<?> addScreen(@RequestBody Loai_ScreenRequest request) {
            String status = service.createLoaiScreen(request);
            if (status.equalsIgnoreCase("ok")) {
                return ResponseEntity.ok().body(request);
            }
            return ResponseEntity.badRequest().body(request);
        }

        @PutMapping
        public ResponseEntity<?> updateScreen(@RequestBody Loai_ScreenRequest request) {
            String status = service.updateLoaiScreen(request);
            if (status.equalsIgnoreCase("ok")) {
                return ResponseEntity.ok().body(request);
            }
            return ResponseEntity.badRequest().body(request);
        }

        @DeleteMapping("{id}")
        public ResponseEntity<?> deleteScreen(@PathVariable("id") Long id) {
            System.out.println("id +   " + id);
            String status = service.deleteLoaiScreen(id);

            if (status.equalsIgnoreCase("ok")) {
                return ResponseEntity.ok().body("ok");
            }
            return ResponseEntity.badRequest().body("false");
        }

    }


