package com.example.demo.board.repository;

import com.example.demo.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    boolean existsById(Integer id);
}
