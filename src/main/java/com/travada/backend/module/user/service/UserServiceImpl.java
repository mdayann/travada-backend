package com.travada.backend.module.user.service;

import com.cloudinary.utils.ObjectUtils;
import com.travada.backend.config.CloudinaryConfig;
import com.travada.backend.module.user.dto.ConfirmationDto;
import com.travada.backend.module.user.dto.CreateUserDto;
import com.travada.backend.module.user.dto.RegistrationDto;
import com.travada.backend.module.user.dto.ResendCodeDto;
import com.travada.backend.module.user.model.User;
import com.travada.backend.module.user.repository.UserRepository;
import com.travada.backend.utils.BaseResponse;
import com.travada.backend.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    RegistrationDto registrationDto = new RegistrationDto();

    private ModelMapperUtil modelMapperUtil = new ModelMapperUtil();

    @Autowired
    CloudinaryConfig cloudinary;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByConfirmationCode(String confirmationCode) {
        return userRepository.findByConfirmationCode(confirmationCode);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    //Registrasi User Baru
    @Override
    public ResponseEntity<?> createNewUser(CreateUserDto createUser,
                                           MultipartFile foto_ktp,
                                           MultipartFile selfie_ktp) {
        User user = modelMapperUtil
                .modelMapperInit()
                .map(createUser, User.class);

        try {

            //Lookup user in database by email and username
            User existEmail = findByEmail(createUser.getEmail());
            User existUsername = findByUsername(createUser.getUsername());

            if (existEmail != null) {
                return new ResponseEntity(new BaseResponse
                        (HttpStatus.BAD_REQUEST,
                                null,
                                "Email sudah digunakan"),
                        HttpStatus.BAD_REQUEST);
            }
            if (existUsername != null) {
                return new ResponseEntity(new BaseResponse
                        (HttpStatus.BAD_REQUEST,
                                null,
                                "Username sudah digunakan"),
                        HttpStatus.BAD_REQUEST);
            }

            //Disable user until they confirm
            createUser.setActive(false);


            //Upload image to cloudinary
            try {
                Map uploadResult = cloudinary.upload(foto_ktp.getBytes(),
                        ObjectUtils.asMap("resourcetype", "auto"));

                user.setFotoKtp(uploadResult.get("url").toString());

                Map uploadResult2 = cloudinary.upload(selfie_ktp.getBytes(),
                        ObjectUtils.asMap("resourcetype", "auto"));

                user.setSelfieKtp(uploadResult2.get("url").toString());


            } catch (IOException e) {
                return new ResponseEntity(new BaseResponse
                        (HttpStatus.BAD_REQUEST,
                                null,
                                "Upload foto gagal"),
                        HttpStatus.BAD_REQUEST);
            }

            //Generate confirmation code
            Random random = new Random();
            int range = 9999 - 1000 + 1;
            int randomCode = random.nextInt(range) + 1000;
            String confirmationCode = Integer.toString(randomCode);
            createUser.setConfirmationCode(confirmationCode);

            //Set encode password
            String encodedPassword = passwordEncoder.encode(createUser.getPassword());

            user.setPassword(encodedPassword);

            user.setUsername(createUser.getUsername());
            user.setEmail(createUser.getEmail());
            user.setNoHp(createUser.getNo_hp());
            user.setNoRekening(createUser.getNo_rekening());
            user.setNoKtp(createUser.getNo_ktp());
            user.setTglLahir(createUser.getTgl_lahir());
            user.setJenisKelamin(createUser.getJenis_kelamin());
            user.setPin(createUser.getPin());
            user.setActive(createUser.isActive());
            user.setConfirmationCode(createUser.getConfirmationCode());

            //Save user
            saveUser(user);

            //Send confirmation email


            emailService.sendMail(createUser.getEmail(),
                    "Konfirmasi Pendaftaran Anda",
                            "Kode anda : " + user.getConfirmationCode());

            registrationDto.setUsername(createUser.getUsername());
            registrationDto.setEmail(createUser.getEmail());

            return new ResponseEntity(new BaseResponse
                    (HttpStatus.OK,
                            registrationDto,
                            "Registrasi Berhasil, Kode konfirmasi berhasil dikirim ke " + createUser.getEmail()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new BaseResponse
                    (HttpStatus.BAD_GATEWAY,
                            null,
                            "Server error"),
                    HttpStatus.BAD_GATEWAY);
        }

    }

    @Override
    public ResponseEntity<?> confirmNewUser(ConfirmationDto confirmUser) {

        try {

            //Find the user associated with associated code
            User existUser = userRepository.findByUsername(confirmUser.getUsername());

            String confirmCode = confirmUser.getConfirmationCode();
            String sentCode = existUser.getConfirmationCode();
            System.out.println(confirmCode==sentCode);

            try {
                if (confirmCode.equals(sentCode) || existUser.isActive()) {

                    existUser.setActive(true);
                    existUser.setConfirmationCode(null);

                    userRepository.save(existUser);

                    return new ResponseEntity(new BaseResponse
                            (HttpStatus.OK,
                                    null,
                                    "Akun anda sudah aktif, silahkan login"),
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity(new BaseResponse
                            (HttpStatus.BAD_REQUEST,
                                    null,
                                    "Kode yang anda masukkan tidak valid"),
                            HttpStatus.BAD_REQUEST);
                }


            } catch (Exception e) {
                return new ResponseEntity(new BaseResponse
                        (HttpStatus.BAD_GATEWAY,
                                null,
                                "Username tidak ditemukan"),
                        HttpStatus.BAD_GATEWAY);
            }

        } catch (Exception e) {
            return new ResponseEntity(new BaseResponse
                    (HttpStatus.BAD_GATEWAY,
                            null,
                            "Server error"),
                    HttpStatus.BAD_GATEWAY);

        }
    }

    @Override
    public ResponseEntity<?> resendCode(ResendCodeDto resendCodeDto) {
        try {

            //Find the user associated with email
            User existUser = userRepository.findByEmail(resendCodeDto.getEmail());

            //If user not found
            if (existUser == null) {

                return new ResponseEntity(new BaseResponse
                        (HttpStatus.BAD_REQUEST,
                                null,
                                "Akun tidak ditemukan, silahkan daftar"),
                        HttpStatus.BAD_REQUEST);
            }

            //If user already active
            if (existUser.isActive()) {

                return new ResponseEntity(new BaseResponse
                        (HttpStatus.OK,
                                null,
                                "Akun anda sudah aktif, silahkan login"),
                        HttpStatus.OK);
            }
            //Set new code to the user
            //Generate confirmation code
            Random random = new Random();
            int range = 9999 - 1000 + 1;
            int randomCode = random.nextInt(range) + 1000;
            String confirmationCode = Integer.toString(randomCode);

            existUser.setConfirmationCode(confirmationCode);

            //Save user
            saveUser(existUser);

            //Send confirmation email
            emailService.sendMail(resendCodeDto.getEmail(),
                    "Konfirmasi Pendaftaran Anda", "Kode Konfirmasi Anda : " + existUser.getConfirmationCode());

            return new ResponseEntity(new BaseResponse
                    (HttpStatus.OK,
                            resendCodeDto,
                            "Resend Kode Berhasil, Kode konfirmasi berhasil dikirim ke " + resendCodeDto.getEmail()),
                    HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(new BaseResponse
                    (HttpStatus.BAD_GATEWAY,
                            null,
                            "Server error"),
                    HttpStatus.BAD_GATEWAY);
        }
    }

}
