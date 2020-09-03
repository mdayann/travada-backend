package com.travada.backend.module.trip.model;

import com.travada.backend.utils.AuditModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "destinasi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Destinasi extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama_trip;
    private String benua;
    private Boolean lokal;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "gambar_trip",joinColumns = @JoinColumn(name = "id_trip"))
    private List<String> gambarList;
    private int kapasitas;
    private Long harga_satuan;
    private String overview;
    private String deskripsi;
    private LocalDate berangkat;
    private LocalDate pulang;
    @Setter(AccessLevel.NONE)
    private int durasi;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "rencana_trip",joinColumns = @JoinColumn(name = "id_trip"))
    private List<RencanaPerjalanan> rencanaList;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "fasilitas_trip", joinColumns = @JoinColumn(name = "id_trip"))
    private Set<String> fasilitas;
    private String info_waktu_cuaca;
    private String info_persiapan;
    private String info_kesehatan_keamanan;

    public void setDurasi(LocalDate berangkat, LocalDate pulang) {
       Period period = Period.between(berangkat, pulang);
       this.durasi = period.getDays();
    }
}
