package com.travada.backend.module.trip.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.travada.backend.config.CloudinaryConfig;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("destinasiServiceImpl")
public class DestinasiServiceImpl implements DestinasiService {
    @Autowired
    private DestinasiRepository destinasiRepository;

    @Autowired
    private CloudinaryConfig cloudc;

    @Transactional
    public Destinasi saveDestinasi(Destinasi destinasi, List<String> photos){
        destinasi.setGambarList(photos);
        Destinasi destinasiResponse = destinasiRepository.save(destinasi);
        return destinasiResponse;
    }

    @Override
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
    public BaseResponse findById(Long id) {
        BaseResponse baseResponse = new BaseResponse();
        Destinasi destinasi = destinasiRepository.findById(id)
                .orElseThrow(null);
        baseResponse.setStatus(HttpStatus.OK);
        baseResponse.setData(destinasi);

        return baseResponse;
    }

    @Transactional
    public BaseResponse editById(Long id, Destinasi newDestinasi) {
        BaseResponse baseResponse = new BaseResponse();
        Destinasi destinasi = destinasiRepository.findById(id)
                .orElseThrow(null);
        destinasi.setBenua(newDestinasi.getBenua());
        destinasi.setBerangkat(newDestinasi.getBerangkat());
        destinasi.setDeskripsi(newDestinasi.getDeskripsi());
        destinasi.setDurasi(newDestinasi.getDurasi());
        destinasi.setFasilitas(newDestinasi.getFasilitas());
        destinasi.setHarga_satuan(newDestinasi.getHarga_satuan());
        destinasi.setInfo_kesehatan_keamanan(newDestinasi.getInfo_kesehatan_keamanan());
        destinasi.setInfo_persiapan(newDestinasi.getInfo_persiapan());
        destinasi.setInfo_waktu_cuaca(newDestinasi.getInfo_waktu_cuaca());
        destinasi.setKapasitas(newDestinasi.getKapasitas());
        destinasi.setLokal(newDestinasi.getLokal());
        destinasi.setNama_trip(newDestinasi.getNama_trip());
        destinasi.setOverview(newDestinasi.getOverview());
        destinasi.setPulang(newDestinasi.getPulang());
        destinasi.setRencanaList(newDestinasi.getRencanaList());
        destinasiRepository.save(destinasi);

        baseResponse.setStatus(HttpStatus.OK);
        baseResponse.setData(destinasi);
        return baseResponse;
    }

    @Transactional
    public ResponseEntity<?> dropDestinasi(Long id) {
        return destinasiRepository.findById(id)
                .map(destinasi -> {
                    destinasiRepository.delete(destinasi);
                    return ResponseEntity.ok().build();
                }).orElseThrow();
    }

}
