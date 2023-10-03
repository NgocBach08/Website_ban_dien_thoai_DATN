package com.example.testhtml.api.attribute;

import com.example.testhtml.dto.request.attribute.screen.ScreenRequest;
import com.example.testhtml.dto.respone.attribute.screen.ScreenReposne;
import com.example.testhtml.service.IScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/screen")
public class ScreenApi {

    @Autowired
    private IScreenService screenService;

    @GetMapping("/{id}")
    public ScreenReposne findById(@PathVariable("id") String id) {
        return screenService.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> addScreen(@RequestBody ScreenRequest request) {
        String status = screenService.save(request);
        if (status.equalsIgnoreCase("ok")) {
            return ResponseEntity.ok().body(request);
        }
        return ResponseEntity.badRequest().body(request);
    }

    @PutMapping
    public ResponseEntity<?> updateScreen(@RequestBody ScreenRequest request) {
        String status = screenService.edit(request);
        if (status.equalsIgnoreCase("ok")) {
            return ResponseEntity.ok().body(request);
        }
        return ResponseEntity.badRequest().body(request);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteScreen(@PathVariable("id") Long id) {
        String status = screenService.delete(id);
        if (status.equalsIgnoreCase("ok")) {
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().body("false");
    }
}
