package com.travada.backend.module.tabungan.service;

import com.cloudinary.utils.ObjectUtils;
import com.travada.backend.config.CloudinaryConfig;
import com.travada.backend.exception.DataNotFoundException;
import com.travada.backend.module.tabungan.model.Save;
import com.travada.backend.module.tabungan.repository.SaveRepository;
import com.travada.backend.module.trip.model.Destinasi;
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

@Service("saveServiceImpl")
public abstract class SaveServiceImpl implements SaveService {
    @Autowired
    private SaveRepository saveRepository;

    @Autowired
    private CloudinaryConfig cloudinaryConfig;

    @Transactional
    public BaseResponse saveSave(Save save, MultipartFile[] foto) {
        BaseResponse baseResponse = new BaseResponse();
        List<String> gambar = new ArrayList<>();
        if (foto != null) {
            for (MultipartFile file : foto) {
                try {
                    Map uploadResult = cloudinaryConfig.upload(file.getBytes(),
                            ObjectUtils.asMap("resourcetype", "auto"));
                    gambar.add((uploadResult.get("url").toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        save.setGambar_tabungan(gambar);
        saveRepository.save(save);
        baseResponse.setStatus(HttpStatus.CREATED);
        baseResponse.setData(save);
        baseResponse.setMessage("New Trava Save Success");

        return baseResponse;
    }

    @Transactional
    public BaseResponse editById(Long id, Save newSave, MultipartFile[]foto) {
        BaseResponse baseResponse = new BaseResponse();

        Save save = saveRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException(id));
        if (foto != null) {
            List<String> gambar = new ArrayList<>();
            for (MultipartFile file : foto) {
                try {
                    Map uploadResult = cloudinaryConfig.upload(file.getBytes(),
                            ObjectUtils.asMap("resourcetype", "auto"));
                    gambar.add(uploadResult.get("url").toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            save.setGambar_tabungan(gambar);
        }
        if (newSave.getTujuan() != null) {
            save.setTujuan(newSave.getTujuan());
        }
        if (newSave.getJumlah_tabungan() != null) {
            save.setJumlah_tabungan(newSave.getJumlah_tabungan());
        }
        if (newSave.getTarget() != null) {
            save.setTarget(newSave.getTarget());
        }

        saveRepository.save(save);

        baseResponse.setStatus(HttpStatus.CREATED);
        baseResponse.setData(save);
        baseResponse.setMessage("Data with id : " + save.getId() + "is updated");
        return baseResponse;
    }

    @Transactional
    public ResponseEntity<?> dropSave(Long id) {
        return saveRepository.findById(id)
                .map(save -> {
                    saveRepository.delete(save);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new DataNotFoundException(id));
    }

    @Transactional
    public BaseResponse findAll() {
        BaseResponse baseResponse = new BaseResponse();
        List<Save> saveList = saveRepository.findAll();

        baseResponse.setStatus(HttpStatus.OK);
        baseResponse.setData(saveRepository);
        baseResponse.setMessage("Success");
        return baseResponse;
    }

    @Transactional
    public BaseResponse findById(Long id) {
        BaseResponse baseResponse = new BaseResponse();
        Save save = saveRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(id));
        save.setId(id);
        baseResponse.setStatus(HttpStatus.OK);
        baseResponse.setData(save);
        baseResponse.setMessage("Tabungan dengan " + id + " telah berhasil");
        return baseResponse;
    }

    @Transactional
    public BaseResponse editById(Long id, Save newSave, MultipartFile[] foto) {
        BaseResponse baseResponse = new BaseResponse();

        Save save = saveRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException(id)
                );
        if (newSave.getTujuan() != null) {
            save.setTujuan(newSave.getTujuan());
        }
        if (newSave.getJumlah_tabungan() != null) {
            save.setJumlah_tabungan(newSave.getJumlah_tabungan());
        }
        if (foto != null) {
            List<String> gambar = new ArrayList<>();
            for (MultipartFile file : foto) {
                try {
                    Map uploadResult = cloudinaryConfig.upload(file.getBytes(),
                            ObjectUtils.asMap("resourcetype", "auto"));
                    gambar.add(uploadResult.get("url").toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            save.setGambar_tabungan(gambar);
        }
        if (newSave.getSetoran_awal() != null) {
            save.setSetoran_awal(newSave.getSetoran_awal());

        }
        if (newSave.getPeriode() != null) {
            save.setPeriode(newSave.getPeriode());

        }
        save.setJumlah_orang(newSave.getJumlah_orang());

        if (newSave.getTarget() != null ) {
            save.setTarget(newSave.getTarget());
        }


        saveRepository.save(save);

        baseResponse.setStatus(HttpStatus.CREATED);
        baseResponse.setData(save);
        baseResponse.setMessage("Data dengan id " + save.getId() + " telah diupdate");
        return baseResponse;
    }
}
