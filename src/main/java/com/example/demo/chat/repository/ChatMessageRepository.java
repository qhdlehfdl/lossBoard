package com.example.demo.chat.repository;

import com.example.demo.chat.entity.ChatMessage;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    //cursorTime 있을때 사용
    @Query("SELECT m FROM ChatMessage m " +
            "WHERE m.chatRoomId = :chatRoomId " +
            "AND (:cursorTime IS NULL OR m.sendTime < :cursorTime) " +
            "ORDER BY m.sendTime DESC")
    List<ChatMessage> findByRoomIdAndSendTimeBeforeOrderBySendTimeDesc(Integer roomID, LocalDateTime cursorTime, Pageable pageable);

    //cursorTime 없을때 사용
    List<ChatMessage> findByChatRoomIdOrderBySendTimeDesc(Integer roomId, Pageable pageable);

}
