package com.example.demo.board.entity;

import com.example.demo.board.dto.request.CreateCommentRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private LocalDateTime write_time;
    private Integer board_id;
    private String writer_id;

    public Comment(CreateCommentRequestDTO dto, Integer boardID, String writerID){
        this.content = dto.getContent();
        this.write_time = LocalDateTime.now();
        this.board_id = boardID;
        this.writer_id = writerID;
    }

    public void modifyComment(CreateCommentRequestDTO dto){
        this.content = dto.getContent();
    }
}
