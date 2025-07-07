package com.example.demo.chat.service;

import com.example.demo.chat.dto.request.ChatMessageRequestDTO;
import com.example.demo.chat.dto.response.ChatMessageResponseDTO;
import com.example.demo.chat.dto.response.CreateChatRoomResponseDTO;
import com.example.demo.chat.dto.response.GetChatMessageResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

public interface ChatService {

    ResponseEntity<? super CreateChatRoomResponseDTO> createChatRoom(Integer boardID, String userID);
    void sendMessage(ChatMessageRequestDTO dto, Integer roomId, String senderId);
    ResponseEntity<? super GetChatMessageResponseDTO> getChatMessage(Integer roomID, String userID, Integer cursorID, int size);
}
