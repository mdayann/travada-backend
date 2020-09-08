package com.travada.backend.module.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateUserDto {

    private String username;

    private String email;

    private String no_hp;

    private Long no_rekening;

    private Long no_ktp;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date tgl_lahir;

    private String jenis_kelamin;

    private Long pin;

    private String password;

    private boolean isActive;

    private String confirmationCode;

}
