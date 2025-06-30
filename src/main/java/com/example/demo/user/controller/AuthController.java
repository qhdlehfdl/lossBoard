package com.example.demo.user.controller;

import com.example.demo.user.dto.request.FindIdRequestDTO;
import com.example.demo.user.dto.request.LoginRequestDTO;
import com.example.demo.user.dto.response.*;
import com.example.demo.user.dto.request.RegisterRequestDTO;
import com.example.demo.user.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<? super RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        ResponseEntity<? super RegisterResponseDTO> response = authService.register(registerRequestDTO);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<? super LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestBody){
        ResponseEntity<? super LoginResponseDTO> response = authService.login(requestBody);
        return response;
    }

    @PostMapping("/find-id")
    public ResponseEntity<? super FindIdResponseDTO> findId(@Valid @RequestBody FindIdRequestDTO findIdRequestDTO){
        ResponseEntity<? super FindIdResponseDTO> response = authService.findId(findIdRequestDTO);
        return response;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<? super RefreshTokenResponseDTO> refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ResponseEntity<? super RefreshTokenResponseDTO> response = authService.refreshToken(httpServletRequest, httpServletResponse);
        return response;
    }

    @DeleteMapping("/logout")
    public ResponseEntity<? super LogoutResponseDTO> logout(HttpServletRequest httpServletRequest){
        ResponseEntity<? super LogoutResponseDTO> response = authService.logout(httpServletRequest);
        return response;
    }
}

