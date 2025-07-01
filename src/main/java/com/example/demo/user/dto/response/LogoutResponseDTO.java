package com.example.demo.user.dto.response;

import com.example.demo.common.ResponseCode;
import com.example.demo.common.ResponseDTO;
import com.example.demo.common.ResponseMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

public class LogoutResponseDTO extends ResponseDTO {

    private LogoutResponseDTO() {super(ResponseCode.SUCCESS,ResponseCode.SUCCESS);}

    public static ResponseEntity<LogoutResponseDTO> success(){
        LogoutResponseDTO result = new LogoutResponseDTO();

        //refresh토큰 쿠키에서 삭제
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh-token")
                .maxAge(0)
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
