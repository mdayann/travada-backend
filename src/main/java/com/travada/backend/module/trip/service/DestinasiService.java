package com.travada.backend.module.trip.service;

import com.travada.backend.module.trip.model.Destinasi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public interface DestinasiService {
    public ResponseEntity<?> saveDestinasi(Destinasi destinasi);

    public String uploadImage(MultipartFile file);

    public List<Destinasi> findAll();

    public List<Destinasi> findAllSortByPopularitas();

    public List<Destinasi> findAllSortByPilihan();

    public List<Destinasi> findAllFilterHarga(int termurah, int termahal, String benua);

    public List<Destinasi> search(String keyword);

    public Destinasi findById(Long id);

    public Destinasi editById(Long id, Destinasi destinasi);

    public ResponseEntity<?> dropDestinasi(Long id);
}
