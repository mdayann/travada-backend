package com.travada.backend.module.trip.controller;

import com.travada.backend.module.trip.model.Destinasi;
import com.travada.backend.module.trip.service.DestinasiService;
import com.travada.backend.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/destinasi")
public class DestinasiController {
    @Autowired
    private DestinasiService destinasiService;

    @GetMapping("/all")
    public List<Destinasi> getAll() {
        return destinasiService.findAll();
    }

    @GetMapping("/{id}")
    public BaseResponse getById(@PathVariable Long id) {
        return destinasiService.findById(id);
    }

    @PostMapping()
    public Destinasi createDestinasi(@RequestBody Destinasi destinasi, @RequestParam MultipartFile files) throws IOException {
        return destinasiService.saveDestinasi(destinasi, files);
    }

    @PutMapping("/{id}")
    public BaseResponse putById(@PathVariable Long id, @RequestBody Destinasi destinasiReq) {
        return destinasiService.editById(id, destinasiReq);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dropDestinasi(@PathVariable Long id){
        return destinasiService.dropDestinasi(id);
    }
}
