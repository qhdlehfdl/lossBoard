package com.example.demo.user.service;

import com.example.demo.user.dto.request.FindIdRequestDTO;
import com.example.demo.user.dto.request.LoginRequestDTO;
import com.example.demo.user.dto.response.*;
import com.example.demo.user.dto.request.RegisterRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<? super RegisterResponseDTO> register(RegisterRequestDTO registerRequestDTO);

    ResponseEntity<? super LoginResponseDTO> login(LoginRequestDTO loginRequestDTO);

    ResponseEntity<? super FindIdResponseDTO> findId(FindIdRequestDTO findIdRequestDTO);

    ResponseEntity<? super RefreshTokenResponseDTO> refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    ResponseEntity<? super LogoutResponseDTO> logout(HttpServletRequest request);
}
