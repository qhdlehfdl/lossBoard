package com.example.demo.chat.controller;

import com.example.demo.chat.dto.response.CreateChatRoomResponseDTO;
import com.example.demo.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    //board id를 가진 board에 관한 채팅방
    @PostMapping("/board/{boardID}/create")
    public ResponseEntity<? super CreateChatRoomResponseDTO> createChatRoom(@PathVariable("boardID")Integer boardID, @AuthenticationPrincipal String userID){
        ResponseEntity<? super CreateChatRoomResponseDTO> response = chatService.createChatRoom(boardID,userID);
        return response;
    }
}
