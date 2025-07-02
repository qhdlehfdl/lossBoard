package com.example.demo.board.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CreateCommentRequestDTO {
    @NotBlank(message = "댓글을 입력해주세요.")
    private String content;
}
