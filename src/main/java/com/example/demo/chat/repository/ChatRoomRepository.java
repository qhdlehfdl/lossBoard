package com.example.demo.chat.repository;

import com.example.demo.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    List<ChatRoom> findAllByBoardId(Integer roomID);
}
