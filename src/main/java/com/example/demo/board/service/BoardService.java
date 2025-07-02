package com.example.demo.board.service;

import com.example.demo.board.dto.request.CreateCommentRequestDTO;
import com.example.demo.board.dto.request.CreatePostRequestDTO;
import com.example.demo.board.dto.response.*;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;

public interface BoardService {

    ResponseEntity<? super CreatePostResponseDTO> createPost(CreatePostRequestDTO createPostRequestDTO, String writerID);
    ResponseEntity<? super DeletePostResponseDTO> deletePost(Integer boardID, String writerID);
    ResponseEntity<? super ModifyPostResponseDTO> modifyPost(CreatePostRequestDTO createPostRequestDTO, Integer boardID, String writerID);
    ResponseEntity<? super CreateCommentResponseDTO> createComment(CreateCommentRequestDTO dto, Integer boardID, String writerID);
    ResponseEntity<? super DeleteCommentResponseDTO> deleteComment(Integer boardID, Integer commentID, String writerID);
    ResponseEntity<? super ModifyCommentResponseDTO> modifyComment(CreateCommentRequestDTO dto, Integer boardID, Integer commentID, String writerID);
}
