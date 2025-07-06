package com.example.demo.chat.repository;

import com.example.demo.chat.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Integer> {

    boolean existsByChatRoomIdAndUserId(Integer roomID, String userID);
    List<ChatRoomMember> findAllByChatRoomId(Integer roomID);
}
