package com.travada.backend.module.trip.repository;

import com.travada.backend.module.trip.model.Destinasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinasiRepository extends JpaRepository<Destinasi, Long> {
    @Query("select d from Destinasi d where d.harga_satuan between ?1 and ?2")
    public List<Destinasi> findAllByFilterHarga(int termurah, int termahal);
}
