package com.example.demo.board.service;

import com.example.demo.board.dto.request.CreateCommentRequestDTO;
import com.example.demo.board.dto.request.CreatePostRequestDTO;
import com.example.demo.board.dto.response.*;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.Comment;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.board.repository.CommentRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

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
    public ResponseEntity<? super DeletePostResponseDTO> deletePost(Integer boardID, String writerID) {

        try{
            boolean existedUser = userRepository.existsById(writerID);
            if(!existedUser) return DeletePostResponseDTO.notExistUser();

            Optional<Board> opt = boardRepository.findById(boardID);
            if(opt.isEmpty()) return DeletePostResponseDTO.notExistBoard();
            Board board = opt.get();

            String userID = board.getWriter_id();
            boolean isWriter = userID.equals(writerID);
            if(!isWriter) return DeletePostResponseDTO.noPermission();

            boardRepository.deleteById(boardID);

        }catch(Exception e){
            e.printStackTrace();
            return DeletePostResponseDTO.databaseError();
        }

        return DeletePostResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super ModifyPostResponseDTO> modifyPost(CreatePostRequestDTO createPostRequestDTO, Integer boardID, String writerID) {
        try{
            boolean existedUser = userRepository.existsById(writerID);
            if(!existedUser) return ModifyPostResponseDTO.notExistedUser();

            Optional<Board> opt = boardRepository.findById(boardID);
            if(opt.isEmpty()) return ModifyPostResponseDTO.notExistBoard();
            Board board = opt.get();

            String userID = board.getWriter_id();
            boolean isWriter = userID.equals(writerID);
            if(!isWriter) return ModifyPostResponseDTO.noPermission();

            board.modifyPost(createPostRequestDTO);
            boardRepository.save(board);

        } catch (Exception e){
            e.printStackTrace();
            return ModifyPostResponseDTO.databaseError();
        }

        return ModifyPostResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super CreateCommentResponseDTO> createComment(CreateCommentRequestDTO dto, Integer boardID, String writerID) {
        try{
            Optional<Board> opt = boardRepository.findById(boardID);
            if(opt.isEmpty()) return CreateCommentResponseDTO.notExistBoard();
            Board board = opt.get();

            boolean existedUser = userRepository.existsById(writerID);
            if(!existedUser) return CreateCommentResponseDTO.notExistedUser();

            Comment comment = new Comment(dto, boardID, writerID);
            commentRepository.save(comment);

            board.increaseCommentCount();
            boardRepository.save(board);
        } catch(Exception e){
            e.printStackTrace();
            return CreateCommentResponseDTO.databaseError();
        }

        return CreateCommentResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super DeleteCommentResponseDTO> deleteComment(Integer boardID, Integer commentID, String writerID) {
        try{
            Optional<Board> optBoard = boardRepository.findById(boardID);
            if(optBoard.isEmpty()) return DeleteCommentResponseDTO.notExistBoard();
            Board board = optBoard.get();

            boolean existedUser = userRepository.existsById(writerID);
            if(!existedUser) return DeleteCommentResponseDTO.notExistUser();

            Optional<Comment> opt = commentRepository.findById(commentID);
            if(opt.isEmpty()) return DeleteCommentResponseDTO.notExistComment();
            Comment comment = opt.get();

            boolean isWriter = writerID.equals(comment.getWriter_id());
            if(!isWriter) return DeleteCommentResponseDTO.noPermission();

            board.decreaseCommentCount();

            boardRepository.save(board);
            commentRepository.deleteById(commentID);
        }catch (Exception e){
            e.printStackTrace();
            return DeleteCommentResponseDTO.databaseError();
        }

        return DeleteCommentResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super ModifyCommentResponseDTO> modifyComment(CreateCommentRequestDTO dto, Integer boardID, Integer commentID, String writerID){
        try{
            boolean existedBoard = boardRepository.existsById(boardID);
            if(!existedBoard) return ModifyCommentResponseDTO.notExistBoard();

            Optional<Comment> optComment = commentRepository.findById(commentID);
            if(optComment.isEmpty()) return ModifyCommentResponseDTO.notExistComment();
            Comment comment = optComment.get();

            boolean existedUser = userRepository.existsById(writerID);
            if(!existedUser) return ModifyCommentResponseDTO.notExistUser();

            boolean isWriter = comment.getWriter_id().equals(writerID);
            if(!isWriter) return ModifyCommentResponseDTO.noPermission();

            comment.modifyComment(dto);
            commentRepository.save(comment);
        }catch (Exception e){
            e.printStackTrace();
            return ModifyCommentResponseDTO.databaseError();
        }

        return ModifyCommentResponseDTO.success();
    }
}
