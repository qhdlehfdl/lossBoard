package com.example.demo.board.controller;

import com.example.demo.board.dto.request.CreatePostRequestDTO;
import com.example.demo.board.dto.response.CreatePostResponseDTO;
import com.example.demo.board.dto.response.DeletePostResponseDTO;
import com.example.demo.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create-post")
    public ResponseEntity<? super CreatePostResponseDTO> createPost(@Valid @RequestBody CreatePostRequestDTO createPostRequestDTO, @AuthenticationPrincipal String writerID){
        ResponseEntity<? super CreatePostResponseDTO> response = boardService.createPost(createPostRequestDTO, writerID);
        return response;
    }

    @DeleteMapping("/delete-post/{boardID}")
    public ResponseEntity<? super DeletePostResponseDTO> deletePost(@PathVariable("boardID") Integer boardID, @AuthenticationPrincipal String writerID){
        ResponseEntity<? super DeletePostResponseDTO> response = boardService.deletePost(boardID, writerID);
        return response;
    }
}
