package com.example.demo.board.controller;

import com.example.demo.board.dto.request.CreateCommentRequestDTO;
import com.example.demo.board.dto.request.CreatePostRequestDTO;
import com.example.demo.board.dto.response.*;
import com.example.demo.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<? super CreatePostResponseDTO> createPost(@Valid @RequestBody CreatePostRequestDTO createPostRequestDTO, @AuthenticationPrincipal String writerID){
        ResponseEntity<? super CreatePostResponseDTO> response = boardService.createPost(createPostRequestDTO, writerID);
        return response;
    }

    @DeleteMapping("/delete/{boardID}")
    public ResponseEntity<? super DeletePostResponseDTO> deletePost(@PathVariable("boardID") Integer boardID, @AuthenticationPrincipal String writerID){
        ResponseEntity<? super DeletePostResponseDTO> response = boardService.deletePost(boardID, writerID);
        return response;
    }

    @PatchMapping("/modify/{boardID}")
    public ResponseEntity<? super ModifyPostResponseDTO> modifyPost(@Valid @RequestBody CreatePostRequestDTO dto, @PathVariable("boardID") Integer boardID, @AuthenticationPrincipal String writerID){
        ResponseEntity<? super ModifyPostResponseDTO> response = boardService.modifyPost(dto, boardID, writerID);
        return response;
    }

    @PostMapping("/{boardID}/comment/create")
    public ResponseEntity<? super CreateCommentResponseDTO> createComment(@Valid @RequestBody CreateCommentRequestDTO dto, @PathVariable("boardID")Integer boardID, @AuthenticationPrincipal String writerID){
        ResponseEntity<? super CreateCommentResponseDTO> response = boardService.createComment(dto, boardID, writerID);
        return response;
    }

    @DeleteMapping("/{boardID}/comment/delete/{commentID}")
    public ResponseEntity<? super DeleteCommentResponseDTO> deleteComment(@PathVariable("boardID")Integer boardID, @PathVariable("commentID")Integer commentID, @AuthenticationPrincipal String writerID){
        ResponseEntity<? super DeleteCommentResponseDTO> response = boardService.deleteComment(boardID,commentID,writerID);
        return response;
    }

    @PatchMapping("/{boardID}/comment/modify/{commentID}")
    public ResponseEntity<? super ModifyCommentResponseDTO> modifyComment(@Valid @RequestBody CreateCommentRequestDTO dto, @PathVariable("boardID")Integer boardID, @PathVariable("commentID")Integer commentID, @AuthenticationPrincipal String writerID){
        ResponseEntity<? super ModifyCommentResponseDTO> response = boardService.modifyComment(dto,boardID,commentID,writerID);
        return response;
    }
}
