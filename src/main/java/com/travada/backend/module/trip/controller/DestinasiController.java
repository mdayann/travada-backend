package com.travada.backend.module.trip.controller;

import com.travada.backend.module.trip.model.Destinasi;
import com.travada.backend.module.trip.service.DestinasiService;
import com.travada.backend.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/destinasi")
public class DestinasiController {
    @Autowired
    private DestinasiService destinasiService;

    @GetMapping("/all")
    public List<Destinasi> getAll() {
        return destinasiService.findAll();
    }
    @GetMapping("/populer")
    public List<Destinasi> getPopuler(){return destinasiService.findAllSortByPopularitas();}
    @GetMapping("/pilihan")
    public List<Destinasi> getPilihan(){return destinasiService.findAllSortByPilihan();}
    @GetMapping("/harga")
    public List<Destinasi> getRangeHarga(@RequestParam int termurah, @RequestParam int termahal, @RequestParam String benua){
        return destinasiService.findAllFilterHarga(termurah,termahal, benua);
    }
    @GetMapping("/pencarian")
    public List<Destinasi> getPencarian(@RequestParam String keyword){
        if(keyword!=null){
            return destinasiService.search(keyword);
        }
        return destinasiService.findAll();
    }

    @GetMapping("/{id}")
    public BaseResponse getById(@PathVariable Long id) {
        BaseResponse response = new BaseResponse();
        response.setData(destinasiService.findById(id));
        return response;
    }

    @PostMapping()
    public ResponseEntity<?> createDestinasi(@ModelAttribute Destinasi destinasi,
                                             @RequestParam MultipartFile file1,
                                             @RequestParam MultipartFile file2,
                                             @RequestParam MultipartFile file3,
                                             @RequestParam MultipartFile file4,
                                             @RequestParam MultipartFile file5) {
        List<String> gambar = new ArrayList<>();
        gambar.add(destinasiService.uploadImage(file1));
        gambar.add(destinasiService.uploadImage(file2));
        gambar.add(destinasiService.uploadImage(file3));
        gambar.add(destinasiService.uploadImage(file4));
        gambar.add(destinasiService.uploadImage(file5));
        destinasiService.saveDestinasi(destinasi, gambar);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putById(@PathVariable Long id, @RequestBody Destinasi destinasiReq) {
        destinasiService.editById(id, destinasiReq);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dropDestinasi(@PathVariable Long id) {
        return destinasiService.dropDestinasi(id);
    }
}
