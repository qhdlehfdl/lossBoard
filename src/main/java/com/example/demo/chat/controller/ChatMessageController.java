package com.example.demo.chat.controller;

import com.example.demo.chat.dto.request.ChatMessageRequestDTO;
import com.example.demo.chat.dto.response.ChatMessageResponseDTO;
import com.example.demo.chat.service.ChatService;
import com.example.demo.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatService chatService;

    //프론트에서 /topic/chat/{roomId} subscribe하고있어야함.
    @MessageMapping("/chat/{roomId}/send")
    public void handleChat(@Payload ChatMessageRequestDTO dto, @DestinationVariable("roomId") Integer roomId, Message<?> message){
        //@DestinationVariable -> MessageMapping있을떄 사용
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(message);
        String senderId = (String) accessor.getSessionAttributes().get("userId");
        chatService.sendMessage(dto, roomId, senderId);
    }


}
