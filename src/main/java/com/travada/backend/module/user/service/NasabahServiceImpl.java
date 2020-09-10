package com.travada.backend.module.user.service;

import com.travada.backend.module.user.dto.ListUserDto;
import com.travada.backend.module.user.model.User;
import com.travada.backend.module.user.repository.UserRepository;
import com.travada.backend.utils.BaseResponse;
import com.travada.backend.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NasabahServiceImpl implements NasabahService {

    @Autowired
    private UserRepository userRepository;

    private ModelMapperUtil modelMapperUtil = new ModelMapperUtil();

    @Override
    public ResponseEntity<?> getAllUser() {
        try {

            List<User> users = userRepository.findAll();
            List<ListUserDto> userDtoList = new ArrayList<>();
            for (User user : users) {
                ListUserDto userDto = modelMapperUtil
                        .modelMapperInit()
                        .map(user, ListUserDto.class);

                userDto.setId(user.getId());
                userDto.setUsername(user.getUsername());
                userDto.setEmail(user.getEmail());
                userDto.setNama_lengkap(user.getNamaLengkap());
                userDto.setJenis_kelamin(user.getJenisKelamin());
                userDto.setTgl_lahir(user.getTglLahir());
                userDto.setActive(user.isActive());
                userDto.setNo_hp(user.getNoHp());
                userDto.setNo_ktp(user.getNoKtp());
                userDto.setNo_rekening(user.getNoRekening());
                userDto.setFotoKtp(user.getFotoKtp());
                userDto.setSelfieKtp(user.getSelfieKtp());

                List<String> tagList = new ArrayList<>();

                tagList.add(user.getStatus());

                userDto.setTags(tagList);

                userDtoList.add(userDto);

            }

            return new ResponseEntity(new BaseResponse
                    (HttpStatus.OK,
                            userDtoList,
                            "Data nasabah berhasil dimuat"),
                    HttpStatus.OK);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(new BaseResponse
                    (HttpStatus.BAD_GATEWAY,
                            null,
                            "Server error"),
                    HttpStatus.BAD_GATEWAY);
        }
    }

    @Override
    public ResponseEntity<?> getDetailUser(Long id) {
        try {

            Optional<User> user = userRepository.findById(id);

            if (user.isPresent() == false) {
                return new ResponseEntity(new BaseResponse
                        (HttpStatus.OK,
                                null,
                                "Nasabah tidak ditemukan"),
                        HttpStatus.OK);

            }

            ListUserDto userDto = new ListUserDto();

            userDto.setId(user.get().getId());
            userDto.setUsername(user.get().getUsername());
            userDto.setEmail(user.get().getEmail());
            userDto.setNama_lengkap(user.get().getNamaLengkap());
            userDto.setJenis_kelamin(user.get().getJenisKelamin());
            userDto.setTgl_lahir(user.get().getTglLahir());
            userDto.setActive(user.get().isActive());
            userDto.setNo_hp(user.get().getNoHp());
            userDto.setNo_ktp(user.get().getNoKtp());
            userDto.setNo_rekening(user.get().getNoRekening());
            userDto.setFotoKtp(user.get().getFotoKtp());
            userDto.setSelfieKtp(user.get().getSelfieKtp());

            List<String> tagList = new ArrayList<>();

            tagList.add(user.get().getStatus());

            userDto.setTags(tagList);


            return new ResponseEntity(new BaseResponse
                    (HttpStatus.OK,
                            userDto,
                            "Data nasabah berhasil dimuat"),
                    HttpStatus.OK);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(new BaseResponse
                    (HttpStatus.BAD_GATEWAY,
                            null,
                            "Server error"),
                    HttpStatus.BAD_GATEWAY);
        }
    }

}
