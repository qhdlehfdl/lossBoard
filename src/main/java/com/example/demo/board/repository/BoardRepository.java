package com.example.demo.board.repository;

import com.example.demo.board.entity.Board;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    boolean existsById(Integer id);
}
