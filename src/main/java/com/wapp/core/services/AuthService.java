package com.wapp.core.services;

import com.wapp.core.Dto.*;
import com.wapp.core.Utils.JwtUtils;
import com.wapp.core.entities.UserEntity;
import com.wapp.core.exceptions.auth.AuthException;
import com.wapp.core.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AuthService {

    private final ModelMapper modelMapper = new ModelMapper().registerModule(new RecordModule());
    private final UserRepository userRepository;

    @Autowired
    private AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<ResponseDto> register(RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByEmail(registerRequestDto.email()))
            throw new AuthException(AuthException.AuthErrorTypeEnum.EMAIL_DUPLICATED, "Email já cadastrado.");

        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!Pattern.matches(passwordPattern, registerRequestDto.password()))
            throw new AuthException(AuthException.AuthErrorTypeEnum.INVALID_CREDENTIALS,
                    "A senha deve ter no mínimo 8 caracteres, incluindo letra maiúscula, minúscula, número e caractere especial");

        UserEntity userEntity = modelMapper.map(registerRequestDto, UserEntity.class);
        userRepository.save(userEntity);

        RegisterResponseDto registerResponseDto =
                new RegisterResponseDto(userEntity.getId(), userEntity.getName(), userEntity.getEmail());

        ResponseDto responseDto = new ResponseDto(registerResponseDto, "Usuário cadastrado com sucesso!", true);
        return ResponseEntity.ok(responseDto);
    }

    public ResponseEntity<ResponseDto> login(LoginRequestDto loginRequestDto) {

        UserEntity userEntity = userRepository.findByEmail(loginRequestDto.email()).orElseThrow(
                () -> new AuthException(AuthException.AuthErrorTypeEnum.INVALID_CREDENTIALS, "Credenciais inválidas."));

        if (!BCrypt.checkpw(loginRequestDto.password(), userEntity.getPassword()))
            throw new AuthException(AuthException.AuthErrorTypeEnum.INVALID_CREDENTIALS, "Credenciais inválidas.");

        String jwtToken = JwtUtils.generateToken(userEntity.getId(), userEntity.getName(), userEntity.getEmail());

        LoginResponseDto loginResponseDto =
                new LoginResponseDto(userEntity.getId(), jwtToken, userEntity.getName(), userEntity.getEmail());

        ResponseDto responseDto = new ResponseDto(loginResponseDto, "Login realizado com sucesso!", true);
        return ResponseEntity.ok(responseDto);
    }

}
