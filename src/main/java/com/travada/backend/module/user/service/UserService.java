package com.travada.backend.module.user.service;

import com.travada.backend.module.user.dto.ConfirmationDto;
import com.travada.backend.module.user.dto.CreateUserDto;
import com.travada.backend.module.user.dto.ResendCodeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface UserService {
    public ResponseEntity<?> createNewUser(CreateUserDto createUser, MultipartFile foto_ktp, MultipartFile selfie_ktp);
    public ResponseEntity<?> confirmNewUser(ConfirmationDto confirmUser);
    public ResponseEntity<?> resendCode(ResendCodeDto resendCodeDto);
}
