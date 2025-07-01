package com.example.demo.board.dto.response;

import com.example.demo.common.ResponseCode;
import com.example.demo.common.ResponseMessage;
import com.example.demo.common.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CreatePostResponseDTO extends ResponseDTO {

    private CreatePostResponseDTO() {super(ResponseCode.SUCCESS,ResponseMessage.SUCCESS);}

    public static ResponseEntity<CreatePostResponseDTO> success(){
        CreatePostResponseDTO result = new CreatePostResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> notExistedUser(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
