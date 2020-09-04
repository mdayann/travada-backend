package com.travada.backend.module.user.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.travada.backend.utils.AuditModel;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class User extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "no_hp")
    private String noHp;

    @Column(name = "no_rekening")
    private Long noRekening;

    @Column(name = "foto_ktp")
    private String fotoKtp;

    @Column(name = "selfie_ktp")
    private String selfieKtp;

    @Column(name = "no_ktp")
    private Long noKtp;

    @Column(name = "tgl_lahir")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date tglLahir;

    @Column(name = "jenis_kelamin")
    private String jenisKelamin;

    @Column(name = "pin")
    private Long pin;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "confirmation_code")
    private String confirmationCode;
}
