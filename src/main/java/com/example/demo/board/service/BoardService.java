package com.example.demo.board.service;

import com.example.demo.board.dto.request.CreatePostRequestDTO;
import com.example.demo.board.dto.response.CreatePostResponseDTO;
import com.example.demo.board.dto.response.DeletePostResponseDTO;
import org.springframework.http.ResponseEntity;

public interface BoardService {

    ResponseEntity<? super CreatePostResponseDTO> createPost(CreatePostRequestDTO createPostRequestDTO, String writerID);

    ResponseEntity<? super DeletePostResponseDTO> deletePost(Integer boardID, String writerID);
}
