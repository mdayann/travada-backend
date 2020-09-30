package com.travada.backend.module.tabungan.service;

import com.travada.backend.module.booking.model.Pemesan;
import com.travada.backend.module.tabungan.model.Penabung;

import java.util.List;

public interface PenabungService {
    Penabung createPenabung(Long idPenabung, Penabung penabung);
    Penabung updatePenabung(Long id, Penabung penabung);

    List<Penabung> getPenabung(Long idPenabung);
}
