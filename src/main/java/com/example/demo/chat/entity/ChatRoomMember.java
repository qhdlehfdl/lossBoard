package com.example.demo.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_room_member")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "chat_room_id")
    private Integer chatRoomId;

    @Column(name = "user_id")
    private String userId;

    public ChatRoomMember(Integer chatRoomId, String userId){
        this.chatRoomId = chatRoomId;
        this.userId = userId;
    }
}
