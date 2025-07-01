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
public class RefreshTokenResponseDTO extends ResponseDTO {
    private String newAccessToken;

    private RefreshTokenResponseDTO(String newAccessToken) {
        super(ResponseCode.SUCCESS, ResponseCode.SUCCESS);
        this.newAccessToken = newAccessToken;
    }

    public static ResponseEntity<RefreshTokenResponseDTO> success(String newAccessToken, String newRefreshToken){
        RefreshTokenResponseDTO result = new RefreshTokenResponseDTO(newAccessToken);

        //refresh토큰 쿠키에 저장
        ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh-token")
                .maxAge(Duration.ofDays(1))
                .sameSite("Strict")
                .build();

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(result);
    }

    public static ResponseEntity<ResponseDTO> invalidRefreshToken() {
        ResponseDTO result = new ResponseDTO(ResponseCode.INVALID_REFRESH_TOKEN, ResponseMessage.INVALID_REFRESH_TOKEN);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

    public static ResponseEntity<ResponseDTO> expiredRefreshToken() {
        ResponseDTO result = new ResponseDTO(ResponseCode.EXPIRED_REFRESH_TOKEN, ResponseMessage.EXPIRED_REFRESH_TOKEN);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}