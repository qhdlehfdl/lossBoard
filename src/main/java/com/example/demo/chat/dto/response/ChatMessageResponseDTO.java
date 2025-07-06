package com.example.demo.chat.dto.response;

import com.example.demo.chat.dto.request.ChatMessageRequestDTO;
import com.example.demo.common.ResponseCode;
import com.example.demo.common.ResponseDTO;
import com.example.demo.common.ResponseMessage;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Getter
public class ChatMessageResponseDTO  extends ResponseDTO {

    private String senderID;
    private String content;
    private LocalDateTime sendTime;

    private ChatMessageResponseDTO(String senderID, String content, LocalDateTime sendTime) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.senderID = senderID;
        this.content=content;
        this.sendTime=sendTime;
    }

    public static ChatMessageResponseDTO success(String senderID, String content, LocalDateTime sendTime){
        ChatMessageResponseDTO result = new ChatMessageResponseDTO(senderID, content, sendTime);
        return result;
    }

    public static ResponseDTO notExistedUser(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return result;
    }

    public static ResponseDTO notExistedRoom(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_ROOM, ResponseMessage.NOT_EXISTED_ROOM);
        return result;
    }

    public static ChatMessageResponseDTO of(String senderID, String content, LocalDateTime sendTime){
        return new ChatMessageResponseDTO(senderID, content, sendTime);
    }
}
