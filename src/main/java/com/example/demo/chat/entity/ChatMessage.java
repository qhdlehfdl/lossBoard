package com.example.demo.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;

    @Column(name="chat_room_id")
    private Integer chatRoomId;

    @Column(name = "sender_id")
    private String senderId;

    @Column(name = "send_time")
    private LocalDateTime sendTime;

    public ChatMessage(String content, Integer chatRoomId, String senderId, LocalDateTime sendTime){
        this.content=content;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.sendTime = sendTime;
    }
}
