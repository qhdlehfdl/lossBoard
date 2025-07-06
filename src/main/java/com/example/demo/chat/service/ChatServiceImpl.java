package com.example.demo.chat.service;

import com.example.demo.board.repository.BoardRepository;
import com.example.demo.chat.dto.request.ChatMessageRequestDTO;
import com.example.demo.chat.dto.response.ChatMessageResponseDTO;
import com.example.demo.chat.dto.response.CreateChatRoomResponseDTO;
import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.chat.entity.ChatRoomMember;
import com.example.demo.chat.repository.ChatMessageRepository;
import com.example.demo.chat.repository.ChatRoomMemberRepository;
import com.example.demo.chat.repository.ChatRoomRepository;
import com.example.demo.common.ResponseCode;
import com.example.demo.common.ResponseDTO;
import com.example.demo.common.ResponseMessage;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public ResponseEntity<? super CreateChatRoomResponseDTO> createChatRoom(Integer boardID, String userID){

        Integer roomID = null;

        try{
            String targetUserID = boardRepository.findById(boardID).get().getWriter_id();
            if(targetUserID == null) return CreateChatRoomResponseDTO.notExistedBoard();

            boolean existedUser = userRepository.existsById(targetUserID);
            if(!existedUser) return CreateChatRoomResponseDTO.notExistedUser();

            boolean existedBoard = boardRepository.existsById(boardID);
            if(!existedBoard) return CreateChatRoomResponseDTO.notExistedBoard();

            List<ChatRoom> rooms = chatRoomRepository.findAllByBoardId(boardID);

            for(ChatRoom room: rooms){
                Integer chatRoomID = room.getId();
                boolean existedRequestUser = chatRoomMemberRepository.existsByChatRoomIdAndUserId(chatRoomID, userID);
                boolean existedTargetUser = chatRoomMemberRepository.existsByChatRoomIdAndUserId(chatRoomID, targetUserID);

                //이미 있다면 바로 room id 리턴
                if(existedRequestUser && existedTargetUser) return CreateChatRoomResponseDTO.success(chatRoomID);
            }

            ChatRoom chatRoom = new ChatRoom(boardID);
            roomID = chatRoomRepository.save(chatRoom).getId();

            ChatRoomMember requestUser = new ChatRoomMember(roomID, userID);
            chatRoomMemberRepository.save(requestUser);

            ChatRoomMember targetUser = new ChatRoomMember(roomID, targetUserID);
            chatRoomMemberRepository.save(targetUser);

        }catch (Exception e){
            e.printStackTrace();
            return CreateChatRoomResponseDTO.databaseError();
        }

        return CreateChatRoomResponseDTO.success(roomID);
    }

    @Override
    public void sendMessage(ChatMessageRequestDTO dto, Integer roomId, String senderId) {

        String content;
        LocalDateTime sendTime;

        try {
            boolean existedRoom = chatRoomRepository.existsById(roomId);
            if(!existedRoom) {
                errorBroadcast(new ResponseDTO(ResponseCode.NOT_EXISTED_ROOM, ResponseMessage.NOT_EXISTED_ROOM),senderId);
                return;
            }

            List<ChatRoomMember> members = chatRoomMemberRepository.findAllByChatRoomId(roomId);
            String receiverId = members.stream()
                    .map(ChatRoomMember::getUserId)
                    .filter(id -> !id.equals(senderId))
                    .findFirst()
                    .orElse(null);

            if (receiverId == null) {
                errorBroadcast(new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER),senderId);
                return;
            }

            content = dto.getContent();
            sendTime = LocalDateTime.now();
            ChatMessage message = new ChatMessage(content, roomId, senderId, sendTime);

            chatMessageRepository.save(message);

            ChatMessageResponseDTO response = ChatMessageResponseDTO.of(senderId, content, sendTime);;

            messagingTemplate.convertAndSend("/topic/chat/"+roomId,response);

        } catch (Exception e) {
            e.printStackTrace();
            errorBroadcast(new ResponseDTO(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR),senderId);
            return;
        }
    }

    public void errorBroadcast(ResponseDTO dto, String senderId){
        messagingTemplate.convertAndSendToUser(senderId,"/queue/errors",dto);
    }
}
