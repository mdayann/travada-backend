package com.travada.backend.module.tabungan.service;

import com.travada.backend.module.tabungan.model.Tabungan;
import com.travada.backend.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface TabunganService {
    BaseResponse saveSave (Tabungan tabungan, MultipartFile[] foto);

    BaseResponse editById(Long id, Tabungan tabungan, MultipartFile[] foto);

    BaseResponse findAll();

    BaseResponse findById(Long id);

    ResponseEntity<?> dropSave(Long id);
}
