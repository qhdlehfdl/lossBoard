package com.example.demo.user.dto.response;

import com.example.demo.common.ResponseCode;
import com.example.demo.common.ResponseDTO;
import com.example.demo.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import java.time.Duration;

@Getter
public class LoginResponseDTO extends ResponseDTO {
    private String accessToken;
    private int expirationTime;

    private LoginResponseDTO(String accessToken){
        super(ResponseCode.SUCCESS, ResponseCode.SUCCESS);
        this.accessToken = accessToken;
        this.expirationTime = 3600;
    }

    public static ResponseEntity<LoginResponseDTO> success(String accessToken, String refreshToken){
        LoginResponseDTO result = new LoginResponseDTO(accessToken);

        //refresh토큰 쿠키에 저장
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh-token")
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(result);
    }

    public static ResponseEntity<ResponseDTO> loginFail(){
        ResponseDTO result = new ResponseDTO(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
