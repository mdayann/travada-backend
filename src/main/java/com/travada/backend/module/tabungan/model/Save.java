package com.travada.backend.module.tabungan.model;

import com.travada.backend.utils.AuditModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Save")
@Table(name = "save")
@Data
public class Save extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tujuan")
    private String tujuan;

    @Column(name = "jumlah_tabungan")
    private Long jumlah_tabungan;

    @Column(name = "gambar_tabungan")
    private  String gambar_tabungan;

    @Column(name = "target")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date target;

    @Column(name = "periode")
    private String periode;

    private Boolean autodebet;

    @Column(name = "setoran_awal")
    private Long setoran_awal;

    private int jumlah_orang;

 }

