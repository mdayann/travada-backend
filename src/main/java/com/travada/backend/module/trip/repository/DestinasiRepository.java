package com.travada.backend.module.trip.repository;

import com.travada.backend.module.trip.model.Destinasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinasiRepository extends JpaRepository<Destinasi, Long> {
    @Query(value = "select d from Destinasi d where d.benua = :benua and d.harga_satuan between ?1 and ?2 order by d.harga_satuan asc",nativeQuery = true)
    public List<Destinasi> findAllByFilterHarga(int termurah, int termahal, @Param("benua") String benua);

    @Query("select d from Destinasi d where concat(d.nama_trip,'', d.overview,'', d.benua) like ?1")
    public List<Destinasi> search(String keyword);
}
