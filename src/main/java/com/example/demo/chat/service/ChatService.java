package com.example.demo.chat.service;

import com.example.demo.chat.dto.request.ChatMessageRequestDTO;
import com.example.demo.chat.dto.response.ChatMessageResponseDTO;
import com.example.demo.chat.dto.response.CreateChatRoomResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

public interface ChatService {

    ResponseEntity<? super CreateChatRoomResponseDTO> createChatRoom(Integer boardID, String userID);
    void sendMessage(ChatMessageRequestDTO dto, Integer roomId, String senderId);
}
