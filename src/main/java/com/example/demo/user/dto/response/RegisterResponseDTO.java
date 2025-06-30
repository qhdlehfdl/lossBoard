package com.example.demo.user.dto.response;

import com.example.demo.common.ResponseCode;
import com.example.demo.common.ResponseMessage;
import lombok.Getter;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class RegisterResponseDTO extends ResponseDTO{
    private RegisterResponseDTO(){
        super(ResponseCode.SUCCESS,ResponseCode.SUCCESS);
    }

    public static ResponseEntity<RegisterResponseDTO> success(){
        RegisterResponseDTO result = new RegisterResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> duplicateID(){
        ResponseDTO result = new ResponseDTO(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    public static ResponseEntity<ResponseDTO> duplicateNickname(){
        ResponseDTO result = new ResponseDTO(ResponseCode.DUPLICATE_NICKNAME,ResponseMessage.DUPLICATE_NICKNAME);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
