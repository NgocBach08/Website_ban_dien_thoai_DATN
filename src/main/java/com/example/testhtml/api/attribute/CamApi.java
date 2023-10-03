package com.example.testhtml.api.attribute;

import com.example.testhtml.dto.request.attribute.cam.CamRequest;
import com.example.testhtml.dto.respone.attribute.cam.CamRespone;
import com.example.testhtml.service.ICamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cam")
public class CamApi {

    @Autowired
    private ICamService service;

    @GetMapping("/{id}")
    public CamRespone findById(@PathVariable("id") String id) {
        return service.findByID(Long.valueOf(id));
    }

    @PostMapping
    public ResponseEntity<?> addCam(@RequestBody CamRequest request) {
        String status = service.save(request);
        if (status.equalsIgnoreCase("ok")) {
            return ResponseEntity.ok().body(request);
        }
        return ResponseEntity.badRequest().body(request);
    }

    @PutMapping
    public ResponseEntity<?> updateCam(@RequestBody CamRequest request) {
        String status = service.update(request);
        if (status.equalsIgnoreCase("ok")) {
            return ResponseEntity.ok().body(request);
        }
        return ResponseEntity.badRequest().body(request);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCam(@PathVariable("id") Long id) {
        String status = service.delete(id);
        if (status.equalsIgnoreCase("ok")) {
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().body("false");
    }
}
