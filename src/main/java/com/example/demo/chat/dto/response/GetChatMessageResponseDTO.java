package com.example.demo.chat.dto.response;

import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.common.ResponseCode;
import com.example.demo.common.ResponseDTO;
import com.example.demo.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetChatMessageResponseDTO extends ResponseDTO {

    private List<ChatMessageResponseDTO> chatMessageList;

    private GetChatMessageResponseDTO(List<ChatMessageResponseDTO> chatMessageList){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.chatMessageList = chatMessageList;
    }

    public static ResponseEntity<GetChatMessageResponseDTO> success(List<ChatMessageResponseDTO> chatMessageList){
        GetChatMessageResponseDTO result = new GetChatMessageResponseDTO(chatMessageList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> noPermission() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
