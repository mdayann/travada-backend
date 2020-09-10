package com.travada.backend.module.user.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface NasabahService {

    public ResponseEntity<?> getAllUser();

    public ResponseEntity<?> getDetailUser(Long id);
}
