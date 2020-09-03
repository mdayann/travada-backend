package com.travada.backend.module.trip.service;

import com.travada.backend.module.trip.model.Destinasi;
import com.travada.backend.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public interface DestinasiService {
    public ResponseEntity<?> saveDestinasi(Destinasi destinasi, List<String> photos);

    public String uploadImage(MultipartFile file);

    public List<Destinasi> findAll();

    public Destinasi findById(Long id);

    public Destinasi editById(Long id, Destinasi destinasi);

    public ResponseEntity<?> dropDestinasi(Long id);
}
