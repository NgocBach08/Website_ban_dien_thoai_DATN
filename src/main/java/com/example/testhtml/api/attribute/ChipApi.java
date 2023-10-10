package com.example.testhtml.api.attribute;

import com.example.testhtml.dto.request.attribute.chip.ChipRequestDto;
import com.example.testhtml.dto.respone.attribute.chip.ChipRespone;
import com.example.testhtml.service.IChipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 *
 * @author: hieu
 * @since: 03/07/2022
 * Project_name: com.example.testhtml.api.attribute
 */
@RestController
@RequestMapping("/api/chip")
public class ChipApi {

    @Autowired
    private IChipService service;

    @GetMapping("/{id}")
    public ChipRespone findByCate(@PathVariable("id") String id) {
        return service.findByCate(id);
    }

    @PostMapping
    public ResponseEntity<?> addChip(@RequestBody ChipRequestDto request) {
        String status = service.createChip(request);
        if (status.equalsIgnoreCase("ok")) {
            return ResponseEntity.ok().body(request);
        }
        return ResponseEntity.badRequest().body(request);
    }

    @PutMapping
    public ResponseEntity<?> updateChip(@RequestBody ChipRequestDto request) {
        String status = service.updateChip(request);
        if (status.equalsIgnoreCase("ok")) {
            return ResponseEntity.ok().body(request);
        }
        return ResponseEntity.badRequest().body(request);
    }


}
