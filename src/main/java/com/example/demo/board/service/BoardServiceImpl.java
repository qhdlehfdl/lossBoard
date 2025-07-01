package com.example.demo.board.service;

import com.example.demo.board.dto.request.CreatePostRequestDTO;
import com.example.demo.board.dto.response.CreatePostResponseDTO;
import com.example.demo.board.dto.response.DeletePostResponseDTO;
import com.example.demo.board.entity.Board;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    public ResponseEntity<?super CreatePostResponseDTO> createPost(CreatePostRequestDTO createPostRequestDTO, String writerID){
        try{
            boolean existedId = userRepository.existsById(writerID);
            if(!existedId) return CreatePostResponseDTO.notExistedUser();

            Board board = new Board(createPostRequestDTO,writerID);
            boardRepository.save(board);
        } catch (Exception e){
            e.printStackTrace();
            return CreatePostResponseDTO.databaseError();
        }

        return CreatePostResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super DeletePostResponseDTO> deletePost(Integer boardID, String wrtierID) {

        try{
            boolean existedUser = userRepository.existsById(wrtierID);
            if(!existedUser) return DeletePostResponseDTO.notExistUser();

            Optional<Board> opt = boardRepository.findById(boardID);
            if(opt.isEmpty()) return DeletePostResponseDTO.notExistBoard();
            Board board = opt.get();

            String userID = board.getWriter_id();
            boolean isWriter = userID.equals(wrtierID);
            if(!isWriter) return DeletePostResponseDTO.noPermission();

            boardRepository.deleteById(boardID);

        }catch(Exception e){
            e.printStackTrace();
            return DeletePostResponseDTO.databaseError();
        }

        return DeletePostResponseDTO.success();
    }


}
