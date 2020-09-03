package com.travada.backend.module.trip.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.travada.backend.config.CloudinaryConfig;
import com.travada.backend.exception.DataNotFoundException;
import com.travada.backend.module.trip.model.Destinasi;
import com.travada.backend.module.trip.repository.DestinasiRepository;
import com.travada.backend.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service("destinasiServiceImpl")
public class DestinasiServiceImpl implements DestinasiService {
    @Autowired
    private DestinasiRepository destinasiRepository;

    @Autowired
    private CloudinaryConfig cloudc;

    @Transactional
    public ResponseEntity<?> saveDestinasi(Destinasi destinasi, List<String> photos){
        destinasi.setGambarList(photos);
        destinasiRepository.save(destinasi);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public String uploadImage(MultipartFile file) {
        String gambar = new String();
        try {
            Map uploadResult =cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype","auto"));
            gambar = uploadResult.get("url").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gambar ;
    }

    @Transactional
    public List<Destinasi> findAll() {
        List<Destinasi> destinasiList = destinasiRepository.findAll();
        return destinasiList;
    }

    @Transactional
    public Destinasi findById(Long id) {
        Destinasi destinasi = destinasiRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException(id));

        return destinasi;
    }

    @Transactional
    public Destinasi editById(Long id, Destinasi newDestinasi) {
        Destinasi destinasi = destinasiRepository.findById(id)
                .orElseThrow(null);
        destinasi.setBenua(newDestinasi.getBenua());
        destinasi.setBerangkat(newDestinasi.getBerangkat());
        destinasi.setPulang(newDestinasi.getPulang());
        destinasi.setDeskripsi(newDestinasi.getDeskripsi());
        destinasi.setDurasi(newDestinasi.getBerangkat(),newDestinasi.getPulang());
        destinasi.setFasilitas(newDestinasi.getFasilitas());
        destinasi.setHarga_satuan(newDestinasi.getHarga_satuan());
        destinasi.setInfo_kesehatan_keamanan(newDestinasi.getInfo_kesehatan_keamanan());
        destinasi.setInfo_persiapan(newDestinasi.getInfo_persiapan());
        destinasi.setInfo_waktu_cuaca(newDestinasi.getInfo_waktu_cuaca());
        destinasi.setKapasitas(newDestinasi.getKapasitas());
        destinasi.setLokal(newDestinasi.getLokal());
        destinasi.setNama_trip(newDestinasi.getNama_trip());
        destinasi.setOverview(newDestinasi.getOverview());

        destinasi.setRencanaList(newDestinasi.getRencanaList());
        destinasiRepository.save(destinasi);

        return destinasi;
    }

    @Transactional
    public ResponseEntity<?> dropDestinasi(Long id) {
        return destinasiRepository.findById(id)
                .map(destinasi -> {
                    destinasiRepository.delete(destinasi);
                    return ResponseEntity.ok().build();
                }).orElseThrow(()->new DataNotFoundException(id));
    }

}
