package com.travada.backend.module.trip.repository;

import com.travada.backend.module.trip.model.Destinasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinasiRepository extends JpaRepository<Destinasi, Long> {
}
