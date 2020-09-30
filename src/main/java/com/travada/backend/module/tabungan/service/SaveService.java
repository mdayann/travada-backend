package com.travada.backend.module.tabungan.service;

import com.travada.backend.module.tabungan.model.Save;
import com.travada.backend.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface SaveService {
    BaseResponse saveSave (Save save, MultipartFile[] foto);

    BaseResponse editById(Long id, Save save, MultipartFile[] foto);

    BaseResponse findAll();

    BaseResponse findById(Long id);

    ResponseEntity<?> dropSave(Long id);
}
