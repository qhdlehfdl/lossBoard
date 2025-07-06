package com.example.demo.chat.dto.response;

import com.example.demo.common.ResponseCode;
import com.example.demo.common.ResponseDTO;
import com.example.demo.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CreateChatRoomResponseDTO extends ResponseDTO {

    private Integer roomID;

    private CreateChatRoomResponseDTO(Integer roomID) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.roomID = roomID;
    }

    //채팅방이 이미 있거나 없으면 만들고 room id 리턴
    public static ResponseEntity<CreateChatRoomResponseDTO> success(Integer roomID){
        CreateChatRoomResponseDTO result = new CreateChatRoomResponseDTO(roomID);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> notExistedUser(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDTO> notExistedBoard(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_BOARD, ResponseMessage.NOT_EXISTED_BOARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
