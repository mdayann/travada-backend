package com.travada.backend.module.user.controller;

import com.travada.backend.module.user.service.NasabahServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NasabahController {

    @Autowired
    private NasabahServiceImpl nasabahService;

    //Get All user
    @GetMapping("/nasabah")
    public ResponseEntity<?> getAllUser() {

        return nasabahService.getAllUser();
    }

    //Get detail user
    @GetMapping("/nasabah/{id}")
    public ResponseEntity<?> getDetailUser(@PathVariable Long id) {

        return nasabahService.getDetailUser(id);
    }
}
