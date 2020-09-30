package com.travada.backend.module.tabungan.service;

import com.cloudinary.utils.ObjectUtils;
import com.travada.backend.config.CloudinaryConfig;
import com.travada.backend.exception.DataNotFoundException;
import com.travada.backend.module.booking.model.Pemesan;
import com.travada.backend.module.tabungan.model.Penabung;
import com.travada.backend.module.tabungan.repository.PenabungRepository;
import com.travada.backend.module.tabungan.repository.SaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service("penabungServiceImpl")
public class PenabungServiceImpl implements PenabungService{

    @Autowired
    PenabungRepository penabungRepository;

    @Autowired
    SaveService saveService;

    @Autowired
    SaveRepository saveRepository;

    @Override
    Penabung createPenabung(Long idSave, Penabung penabung) {
        saveRepository.findById(idSave).map(save -> {
            penabung.setSave(save);
            return penabungRepository.save(penabung);
        }).orElseThrow(() -> new DataNotFoundException(idSave));

        return penabung;
    }


    @Override
    public List<Penabung> getPenabung(Long idPenabung) {
        return null;
    }


}
