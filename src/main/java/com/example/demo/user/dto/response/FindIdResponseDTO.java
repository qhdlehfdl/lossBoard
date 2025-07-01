package com.example.demo.user.dto.response;

import com.example.demo.common.ResponseCode;
import com.example.demo.common.ResponseDTO;
import com.example.demo.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class FindIdResponseDTO extends ResponseDTO {
    private String id;

    private FindIdResponseDTO(String id){
        super(ResponseCode.SUCCESS,ResponseCode.SUCCESS);
        this.id = id;
    }

    public static ResponseEntity<FindIdResponseDTO> success(String id){
        FindIdResponseDTO result = new FindIdResponseDTO(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> findFail(){
        ResponseDTO result = new ResponseDTO(ResponseCode.MISMATCH_FAIL, ResponseMessage.MISMATCH_FAIL);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }



}
