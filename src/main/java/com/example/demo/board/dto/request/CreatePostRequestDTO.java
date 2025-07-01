package com.example.demo.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePostRequestDTO {
    @NotBlank(message = "제목 입력은 필수입니다.")
    private String title;

    @NotBlank(message = "본문 입력은 필수입니다.")
    private String content;
}
