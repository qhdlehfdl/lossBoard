package com.example.demo.board.entity;

import com.example.demo.board.dto.request.CreatePostRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Board {

    //@GeneratedValue ->auto increment 역할
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String writer_id;
    private int comment_count;
    private LocalDateTime write_time;

    public Board(CreatePostRequestDTO dto, String writerID){
        this.title=dto.getTitle();
        this.content = dto.getContent();
        this.writer_id = writerID;
        this.comment_count = 0;
        this.write_time = LocalDateTime.now();
    }

    public void modifyPost(CreatePostRequestDTO dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void increaseCommentCount(){
        this.comment_count++;
    }
    public void decreaseCommentCount(){this.comment_count--;}
}
