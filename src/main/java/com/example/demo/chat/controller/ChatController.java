package com.example.demo.chat.controller;

import com.example.demo.chat.dto.response.CreateChatRoomResponseDTO;
import com.example.demo.chat.dto.response.GetChatMessageResponseDTO;
import com.example.demo.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    //board id를 가진 board에 관한 채팅방
    @PostMapping("/board/{boardID}/create")
    public ResponseEntity<? super CreateChatRoomResponseDTO> createChatRoom(@PathVariable(value = "boardID")Integer boardID, @AuthenticationPrincipal String userID){
        ResponseEntity<? super CreateChatRoomResponseDTO> response = chatService.createChatRoom(boardID,userID);
        return response;
    }
    @GetMapping("/room/{roomID}/history")
    public ResponseEntity<? super GetChatMessageResponseDTO> getChatMessage(@PathVariable(value = "roomID") Integer roomID, @AuthenticationPrincipal String userID, @RequestParam(value = "cursorID", required = false) Integer cursorID, @RequestParam(value = "size", defaultValue = "50") int size) {
        ResponseEntity<? super GetChatMessageResponseDTO> response = chatService.getChatMessage(roomID, userID, cursorID, size);
        return response;
    }
}
