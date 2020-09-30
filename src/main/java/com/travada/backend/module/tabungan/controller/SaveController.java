package com.travada.backend.module.tabungan.controller;

import com.travada.backend.module.tabungan.model.Save;
import com.travada.backend.module.tabungan.service.SaveService;
import com.travada.backend.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/save")
public class SaveController {
    @Autowired
    private SaveService saveService;

    @PostMapping()
    public BaseResponse createSave(@ModelAttribute Save save,
                                   @RequestParam MultipartFile[] foto){
        return saveService.saveSave(save, foto);
    }

    @GetMapping("/all")
    public BaseResponse getAll() {
        return saveService.findAll();
    }

    @GetMapping("/{id}")
    public BaseResponse getById(@PathVariable Long id) {
        return saveService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dropSave(@PathVariable Long id){
        return saveService.dropSave(id);
    }
}
